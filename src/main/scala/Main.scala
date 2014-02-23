package se.ramn

import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ByteArrayOutputStream

import javax.xml.transform.Transformer
import javax.xml.transform.TransformerConfigurationException
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.Source
import javax.xml.transform.stream.StreamSource
import javax.xml.transform.Result
import javax.xml.transform.stream.StreamResult
import java.io.StringReader


object Main extends App {
  XsltLab
}

object XsltLab {
  val myXsl = "birds.xsl"
  val myXml = "birds.xml"

  val outputStream = new ByteArrayOutputStream()
  //val outputStream = new FileOutputStream("birds.out")

  //val source: Source = new StreamSource(myXml)
  val xmlSource: Source = new StreamSource(new StringReader(XmlData.birds.toString))
  val result: Result = new StreamResult(outputStream)
  //val xslSource: Source = new StreamSource(myXsl)
  val xslSource: Source = new StreamSource(new StringReader(Xsl.birds.toString))


  val transformer = buildTransformer(xslSource)
  transformer.transform(xmlSource, result)
  println(outputStream.toString)

  def buildTransformer(xslSource: Source): Transformer = {
    val transformerFactory = TransformerFactory.newInstance()
    transformerFactory.newTransformer(xslSource)
  }

}

object XmlData {
  val birds =
    <Class>
      <Order Name="PELECANIFORMES">
        <Family Name="PHAETHONTIDAE">
          <Species Scientific_Name="Phaethon lepturus">White-tailed Tropicbird.</Species>
          <Species Scientific_Name="Phaethon aethereus">Red-billed Tropicbird.</Species>
          <Species Scientific_Name="Phaethon rubricauda">Red-tailed Tropicbird.</Species>
        </Family>
        <Family Name="SULIDAE">
          <Species Scientific_Name="Sula dactylatra">Masked Booby.</Species>
          <Species Scientific_Name="Sula nebouxii">Blue-footed Booby.</Species>
          <Species Scientific_Name="Sula variegata">Peruvian Booby. (A)</Species>
          <Species Scientific_Name="Sula leucogaster">Brown Booby.</Species>
          <Species Scientific_Name="Sula sula">Red-footed Booby.</Species>
          <Species Scientific_Name="Morus bassanus">Northern Gannet.</Species>
          Exclude me
        </Family>
      </Order>
    </Class>
}

object Xsl {
  val birds =
  <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="Class">
      <BirdInfo>
        <xsl:apply-templates select="Order"/>
      </BirdInfo>
    </xsl:template>

    <xsl:template match="Order">
      Order is:  <xsl:value-of select="@Name"/>
       <xsl:apply-templates select="Family"/>
      <xsl:text>
      </xsl:text>
    </xsl:template>

    <xsl:template match="Family">
        Family is:  <xsl:value-of select="@Name"/>
      <!--<xsl:apply-templates select="Species | SubFamily | text()"/>-->
      <!--<xsl:apply-templates select="*"/>-->
      <xsl:apply-templates select="Species"/>
    </xsl:template>

    <xsl:template match="SubFamily">
      SubFamily is <xsl:value-of select="@Name"/>
      <!--<xsl:apply-templates select="Species | text()"/>-->
      <xsl:apply-templates />
    </xsl:template>

    <xsl:template match="Species">
      <xsl:text>
      </xsl:text>
      <xsl:choose>
        <xsl:when test="name(..)='SubFamily'">
          <xsl:text>  </xsl:text><xsl:value-of select="."/><xsl:text> </xsl:text><xsl:value-of select="@Scientific_Name"/>
        </xsl:when>
        <xsl:otherwise>
          <!--<xsl:value-of select="."/><xsl:text> </xsl:text><xsl:value-of select="@Scientific_Name"/>-->
          <xsl:text>    </xsl:text><xsl:value-of select="@Scientific_Name"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:template>
  </xsl:stylesheet>
}
