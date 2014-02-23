package se.ramn

import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ByteArrayOutputStream

import javax.xml.transform.Transformer
import javax.xml.transform.TransformerConfigurationException
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource


object Main extends App {
  val myXsl = "birds.xsl"
  val myXml = "birds.xml"

  val transformer = buildTransformer(myXsl)
  val outputStream = new ByteArrayOutputStream()
  //val outputStream = new FileOutputStream("birds.out")

  transformer.transform(
    new StreamSource(myXml),
    new StreamResult(outputStream))

  println(outputStream.toString)

  def buildTransformer(xslFile: String): Transformer = {
    val transformerFactory = TransformerFactory.newInstance()
    transformerFactory.newTransformer(new StreamSource(xslFile))
  }
}
