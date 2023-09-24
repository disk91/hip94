/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2020.
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 *    and associated documentation files (the "Software"), to deal in the Software without restriction,
 *    including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *    sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies or
 *    substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *    OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.disk91.hip94.api;

import com.disk91.hip94.api.itf.ActionResult;
import com.disk91.hip94.api.itf.HotspotLiteRespItf;
import com.disk91.hip94.data.object.Hotspot;
import com.disk91.hip94.service.HotspotService;
import fr.ingeniousthings.tools.ITNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Tag( name = "hotspot api", description = "hotspot api" )
@CrossOrigin
@RequestMapping(value = "/hotspot/3.0")
@RestController
public class HotspotApi {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HotspotService hotspotService;

    @Operation(summary = "Get Hotspot details",
            description = "Get Hotspot details",
            responses = {
                @ApiResponse(responseCode = "200", description= "Done", content = @Content(schema = @Schema(implementation = Hotspot.class))),
                @ApiResponse(responseCode = "204", description= "No content", content = @Content(schema = @Schema(implementation = ActionResult.class)))
            }
    )
    @RequestMapping(value="/{hotspotId}/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method= RequestMethod.GET)
    public ResponseEntity<?> getHotspotDetails(
            HttpServletRequest request,
            @Parameter(required = true, name = "hotspotId", description = "Base58 hotspot public key encoded")
            @PathVariable String hotspotId
    ) {
        try {
            Hotspot hs = hotspotService.getOneExistingHotspot(hotspotId);
            return new ResponseEntity<>(hs, HttpStatus.OK);
        } catch (ITNotFoundException x) {
            return new ResponseEntity<>(ActionResult.NODATA(), HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Find Hotspot by animal name",
        description = "Search hotspot with animal name",
        responses = {
            @ApiResponse(responseCode = "200", description= "Done",
                content = @Content(array = @ArraySchema(schema = @Schema( implementation = HotspotLiteRespItf.class)))),
            @ApiResponse(responseCode = "204", description= "No content", content = @Content(schema = @Schema(implementation = ActionResult.class)))
        }
    )
    @RequestMapping(value="/search/{animal}/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.GET)
    public ResponseEntity<?> getHotspotByAnimal(
        HttpServletRequest request,
        @Parameter(required = true, name = "animal", description = "animal name with - between words")
        @PathVariable String animal
    ) {
        try {
            List<HotspotLiteRespItf> r = hotspotService.getHotspotsByAnimal(animal);
            return new ResponseEntity<>(r, HttpStatus.OK);
        } catch ( ITNotFoundException x ) {
            return new ResponseEntity<>(ActionResult.NODATA(), HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Get Hotspot in a given area",
        description = "Get Hotspot in a given area",
        responses = {
            @ApiResponse(responseCode = "200", description= "Done",
                content = @Content(array = @ArraySchema(schema = @Schema( implementation = HotspotLiteRespItf.class)))),
            @ApiResponse(responseCode = "204", description= "No content", content = @Content(schema = @Schema(implementation = ActionResult.class)))
        }
    )
    @RequestMapping(value="/{latN}/{latS}/{lonW}/{lonE}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        method= RequestMethod.GET)
    public ResponseEntity<?> getHotspotListInAZone(
        HttpServletRequest request,
        @Parameter(required = true, name = "latN", description = "Lat North of the zone")
        @PathVariable double latN,
        @Parameter(required = true, name = "latS", description = "Lat South of the zone")
        @PathVariable double latS,
        @Parameter(required = true, name = "lonW", description = "Longitude West of the zone")
        @PathVariable double lonW,
        @Parameter(required = true, name = "lonE", description = "Longitude East of the zone")
        @PathVariable double lonE
    ) {
        try {
            List<HotspotLiteRespItf> hs = hotspotService.getHotspotInAZone(latN,latS,lonW,lonE);
            return new ResponseEntity<>(hs, HttpStatus.OK);
        } catch (ITNotFoundException x) {
            return new ResponseEntity<>(ActionResult.NODATA(), HttpStatus.NO_CONTENT);
        }
    }



}

