<?xml version="1.0" ?>
	<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output indent="no" method="text" encoding="utf-8" />
  <xsl:strip-space elements="*" />

<xsl:template match="/CompactData/DataSet/Series">
	<xsl:variable name="NAME" select="./@NAME" />
	<xsl:variable name="DESCRIPTION" select="./@DESCRIPTION" />
	<xsl:variable name="FREQ" select="./@FREQ" />
	<xsl:variable name="TIME_FORMAT" select="./@TIME_FORMAT" />
	<xsl:variable name="COLLECTION" select="./@COLLECTION" />
	<xsl:variable name="UNIT_MULT" select="./@UNIT_MULT" />
	<xsl:variable name="DECIMALS" select="./@DECIMALS" />
	<xsl:variable name="DATA_TYPE" select="./@DATA_TYPE" />
	<xsl:variable name="GROUP" select="./@GROUP" />
	<xsl:variable name="GEOGRAPHY" select="./@GEOGRAPHY" />
	<xsl:variable name="SHORT_SOURCE" select="./@SHORT_SOURCE" />
	<xsl:variable name="LONG_SOURCE" select="./@LONG_SOURCE" />
    <xsl:variable name="OBS_STATUS" select="./Obs[1]/@OBS_STATUS" />

    <xsl:value-of select="$NAME"/>
    <xsl:text>,</xsl:text>
    <xsl:value-of select="$DESCRIPTION"/>
    <xsl:text>,</xsl:text>
    <xsl:value-of select="$FREQ" />
    <xsl:text>,</xsl:text>
    <xsl:value-of select="$TIME_FORMAT" />
    <xsl:text>,</xsl:text>
    <xsl:value-of select="$COLLECTION" />
    <xsl:text>,</xsl:text>
    <xsl:value-of select="$UNIT_MULT" />
    <xsl:text>,</xsl:text>
    <xsl:value-of select="$DECIMALS" />
    <xsl:text>,</xsl:text>
    <xsl:value-of select="$DATA_TYPE"/>
    <xsl:text>,</xsl:text>
    <xsl:value-of select="$GROUP"/>
    <xsl:text>,</xsl:text>
    <xsl:value-of select="$GEOGRAPHY"/>
    <xsl:text>,</xsl:text>
    <xsl:value-of select="$SHORT_SOURCE" />
    <xsl:text>,</xsl:text>
    <xsl:value-of select="$LONG_SOURCE" />
    <xsl:text>,</xsl:text>
    <xsl:value-of select="$OBS_STATUS" />
       <xsl:apply-templates select="Obs"/>
    <xsl:text>
</xsl:text>

    </xsl:template>

    <xsl:template match="Obs">
        <xsl:text>,</xsl:text>
        <xsl:value-of select="./@TIME_PERIOD"/>
        <xsl:text>:</xsl:text>
        <xsl:value-of select="./@OBS_VALUE"/>

    </xsl:template>

</xsl:stylesheet>