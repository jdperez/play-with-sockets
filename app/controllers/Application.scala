package controllers

import java.io._
import java.text._
import play.api._
import play.api.mvc._
import play.api.libs.{Comet}
import play.api.libs.iteratee._
import play.api.libs.concurrent._
import tshrdlu.twitter._
import twitter4j._
import scala.collection.mutable._
import scala.concurrent.duration._
import play.api.libs.concurrent.Execution.Implicits._


trait BasicStreamer extends StatusListener{
  def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
  def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
  def onException(ex: Exception) { } 
  def onScrubGeo(arg0: Long, arg1: Long) {}
  def onStallWarning(warning: StallWarning) {}
}

object Application extends Controller with BasicStreamer { 
  val twitterStream = new TwitterStreamFactory().getInstance
  val queue = Queue[String]()
  val maxTweets = 20
  twitterStream.addListener(this)
  twitterStream.sample()
  
  /*def index = Action { 
    twitterStream.sample()
    val x = twitterStream.sample()
    Ok("init")
  }*/

  def index = WebSocket.adapter {implicit req =>
    var testMsg = ""
    Enumeratee.map[String] {msg =>
      println(msg)
      if (msg contains "tweet") {
        if (!queue.isEmpty)
          testMsg = queue.dequeue 
        else
          testMsg = "No tweet to give"
      }
      val response = "User sent "+ msg
      print("test message is "+testMsg)
      testMsg
    }
  }

  /*
  def mySocket = WebSocket.using[String] { request =>
    println("in index")
    var clientInput = ""
    val in = Iteratee.foreach[String](s => {
      clientInput += s
      println("received client message: "+s)
      })
      .mapDone { _ =>
      println("Client Disconnected")
    }
    //val out = Enumerator("What up homie?")
    println("client input is" + clientInput)
    val out = Enumerator(clientInput)
    (in,out)
  }
  */
  
  def onStatus(status: twitter4j.Status) {
    if (queue.size == 20) {   
      queue.enqueue(status.getText)
      queue.dequeue
    } else {
      queue.enqueue(status.getText)
    }
  }

}
