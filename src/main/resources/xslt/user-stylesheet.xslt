<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
            </head>
            <div class="items">
                <xsl:apply-templates/>
            </div>
        </html>
    </xsl:template>

    <xsl:template match="user">
        <div class="item">
            <xsl:element name="h2">
                <xsl:value-of select="name"/>
            </xsl:element>
            <ul>
                <xsl:element name="li">
                    <xsl:value-of select="name"/>
                </xsl:element>
                <xsl:element name="li">
                    <xsl:value-of select="userName"/>
                </xsl:element>
                <xsl:element name="li">
                    <xsl:value-of select="city"/>
                </xsl:element>
                <xsl:element name="li">
                    <xsl:value-of select="description"/>
                </xsl:element>
                <xsl:element name="li">
                    <xsl:value-of select="dateOfJoining"/>
                </xsl:element>
                <xsl:element name="li">
                    <xsl:value-of select="exactDob"/>
                </xsl:element>
                <xsl:element name="li">
                    <xsl:choose>
                        <xsl:when test="status=1">
                            <xsl:text>active</xsl:text>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:text>inactive</xsl:text>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:element>
            </ul>
        </div>
    </xsl:template>
</xsl:stylesheet>