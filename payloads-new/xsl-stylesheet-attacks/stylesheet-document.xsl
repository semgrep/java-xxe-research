<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
<xsl:template match="/">
<xsl:value-of select="document('http://localhost:8090')"/>
</xsl:template>
</xsl:stylesheet>