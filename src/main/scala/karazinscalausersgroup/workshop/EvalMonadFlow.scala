package karazinscalausersgroup.workshop

import cats.Eval

object EvalMonadFlow extends App {

  trait Message
  trait DbResponse
  trait Property

  trait QueueConnections {
    def readMessage: Message = new Message {
      override def toString = "Message"
    }
  }

  trait DbConnection {
    def persistMessage(message: Message): DbResponse = new DbResponse {
      override def toString = "DbResponse"
    }
  }

  def getProperty: Property = new Property {}
  def queueConnections(p: Property): QueueConnections = new QueueConnections {}
  def dbConnections: DbConnection = new DbConnection {}


}
