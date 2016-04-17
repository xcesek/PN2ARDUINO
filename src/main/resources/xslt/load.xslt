<?xml version="1.0" encoding="UTF-8"?>
<!--
Copyright (C) 2008-2010 Martin Riesz <riesz.martin at gmail.com>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
-->
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" omit-xml-declaration="no" indent="yes"/>

    <xsl:template match="/document">
        <document>
            <xsl:call-template name="subnet"/>
            <!--<xsl:call-template name="roles"/>-->
            <xsl:call-template name="arduinoManager"/>
        </document>
    </xsl:template>

    <xsl:template name="subnet">
        <id><xsl:value-of select="id"/></id>
        <x><xsl:value-of select="x"/></x>
        <y><xsl:value-of select="y"/></y>
        <label><xsl:value-of select="label"/></label>
        <xsl:for-each select="place">
            <place>
                <id><xsl:value-of select="id"/></id>
                <x><xsl:value-of select="x"/></x>
                <y><xsl:value-of select="y"/></y>
                <label><xsl:value-of select="label"/></label>
                <capacity><xsl:value-of select="capacity"/></capacity>
                <tokens><xsl:value-of select="tokens"/></tokens>
                <isStatic><xsl:value-of select="isStatic"/></isStatic>
                <arduinoNodeExtension>
                    <enabled><xsl:value-of select="arduinoNodeExtension/enabled"/></enabled>
                    <pin><xsl:value-of select="arduinoNodeExtension/pin"/></pin>
                    <function><xsl:value-of select="arduinoNodeExtension/function"/></function>
                    <delayOccurrenceType><xsl:value-of select="arduinoNodeExtension/delayOccurrenceType"/></delayOccurrenceType>
                    <inverseLogic><xsl:value-of select="arduinoNodeExtension/inverseLogic"/></inverseLogic>
                    <thresholdRangeLow><xsl:value-of select="arduinoNodeExtension/thresholdRangeLow"/></thresholdRangeLow>
                    <thresholdRangeHigh><xsl:value-of select="arduinoNodeExtension/thresholdRangeHigh"/></thresholdRangeHigh>
                </arduinoNodeExtension>
            </place>
        </xsl:for-each>
        <xsl:for-each select="transition">
            <transition>
                <id><xsl:value-of select="id"/></id>
                <x><xsl:value-of select="x"/></x>
                <y><xsl:value-of select="y"/></y>
                <label><xsl:value-of select="label"/></label>
                <earliestFiringTime><xsl:value-of select="earliestFiringTime"/></earliestFiringTime>
                <latestFiringTime><xsl:value-of select="latestFiringTime"/></latestFiringTime>
                <priority><xsl:value-of select="priority"/></priority>
                <arduinoNodeExtension>
                    <enabled><xsl:value-of select="arduinoNodeExtension/enabled"/></enabled>
                    <pin><xsl:value-of select="arduinoNodeExtension/pin"/></pin>
                    <function><xsl:value-of select="arduinoNodeExtension/function"/></function>
                    <delayOccurrenceType><xsl:value-of select="arduinoNodeExtension/delayOccurrenceType"/></delayOccurrenceType>
                    <inverseLogic><xsl:value-of select="arduinoNodeExtension/inverseLogic"/></inverseLogic>
                    <thresholdRangeLow><xsl:value-of select="arduinoNodeExtension/thresholdRangeLow"/></thresholdRangeLow>
                    <thresholdRangeHigh><xsl:value-of select="arduinoNodeExtension/thresholdRangeHigh"/></thresholdRangeHigh>
                </arduinoNodeExtension>
            </transition>
        </xsl:for-each>
        <xsl:for-each select="arc">
            <arc>
                <type><xsl:value-of select="type"/></type>
                <sourceId><xsl:value-of select="sourceId"/></sourceId>
                <destinationId><xsl:value-of select="destinationId"/></destinationId>
                <multiplicity><xsl:value-of select="multiplicity"/></multiplicity>
                <xsl:for-each select="breakPoint">
                    <breakPoint>
                        <x><xsl:value-of select="x"/></x>
                        <y><xsl:value-of select="y"/></y>
                    </breakPoint>
                </xsl:for-each>
            </arc>
        </xsl:for-each>
        <xsl:for-each select="subnet">
            <subnet>
                <xsl:call-template name="subnet"/>
            </subnet>
        </xsl:for-each>
        <xsl:for-each select="rootSubnet">
            <subnet>
                <xsl:call-template name="subnet"/>
            </subnet>
        </xsl:for-each>
        <xsl:for-each select="referencePlace">
            <referencePlace>
                <id><xsl:value-of select="id"/></id>
                <x><xsl:value-of select="x"/></x>
                <y><xsl:value-of select="y"/></y>
                <connectedPlaceId><xsl:value-of select="connectedPlaceId"/></connectedPlaceId>
            </referencePlace>
        </xsl:for-each>
        <xsl:for-each select="referenceArc">
            <referenceArc>
                <placeId><xsl:value-of select="placeId"/></placeId>
                <subnetId><xsl:value-of select="subnetId"/></subnetId>
                <xsl:for-each select="breakPoint">
                    <breakPoint>
                        <x><xsl:value-of select="x"/></x>
                        <y><xsl:value-of select="y"/></y>
                    </breakPoint>
                </xsl:for-each>
            </referenceArc>
        </xsl:for-each>
    </xsl:template>

    <!--<xsl:template name="roles">-->
        <!--<roles>-->
            <!--<xsl:for-each select="roles/role">-->
                <!--<role>-->
                    <!--<id><xsl:value-of select="id"/></id>-->
                    <!--<name><xsl:value-of select="name"/></name>-->
                    <!--<xsl:for-each select="transitionId">-->
                        <!--<transitionId><xsl:value-of select="."/></transitionId>-->
                    <!--</xsl:for-each>-->
                    <!--<createCase><xsl:value-of select="createCase"/></createCase>-->
                    <!--<destroyCase><xsl:value-of select="destroyCase"/></destroyCase>-->
                <!--</role>-->
            <!--</xsl:for-each>-->
        <!--</roles>-->
    <!--</xsl:template>-->

    <xsl:template name="arduinoManager">
        <arduinoManager>
            <board><xsl:value-of select="arduinoManager/board"/></board>
            <port><xsl:value-of select="arduinoManager/port"/></port>
            <verbosetOutput><xsl:value-of select="arduinoManager/verbosetOutput"/></verbosetOutput>
            <preserveTempFiles><xsl:value-of select="arduinoManager/preserveTempFiles"/></preserveTempFiles>
            <timingPolicyType><xsl:value-of select="arduinoManager/timingPolicyType"/></timingPolicyType>
            <firingPolicyType><xsl:value-of select="arduinoManager/firingPolicyType"/></firingPolicyType>
        </arduinoManager>
    </xsl:template>

</xsl:transform>