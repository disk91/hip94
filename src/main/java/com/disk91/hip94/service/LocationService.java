package com.disk91.hip94.service;

import com.disk91.hip94.EtlConfig;
import com.disk91.hip94.data.object.Hotspot;
import com.disk91.hip94.service.itf.HotspotBrand;
import com.disk91.hip94.service.itf.HotspotPosition;
import fr.ingeniousthings.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class LocationService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // ========================================================
    // API Backend
    // ========================================================

    @Autowired
    protected EtlConfig forwarderConfig;

    /**
     * Get Api Headers
     */
    private HttpEntity<String> createHeaders(boolean withAuth){

        HttpHeaders headers = new HttpHeaders();
        ArrayList<MediaType> accept = new ArrayList<>();
        accept.add(MediaType.APPLICATION_JSON);
        headers.setAccept(accept);
        headers.add(HttpHeaders.USER_AGENT,"disk91_forwarder/1.0");
        if ( withAuth && forwarderConfig.getHeliumPositionUser().length() > 2 ) {
            String auth = forwarderConfig.getHeliumPositionUser() + ":" + forwarderConfig.getHeliumPositionPass();
            byte[] encodedAuth = Base64.getEncoder().encode(
                    auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            headers.add(HttpHeaders.AUTHORIZATION, authHeader);
        }
        return new HttpEntity<String>(headers);

    }


    public HotspotPosition loadHotspotPosition(String hotspotID) throws ITNotFoundException, ITParseException {

        RestTemplate restTemplate = new RestTemplate();
        String url="";
        try {
            HttpEntity<String> he;
            he = createHeaders(true);
            url = forwarderConfig.getHeliumPositionUrl();
            url = url.replace("{hs}",hotspotID);
            ResponseEntity<HotspotPosition> responseEntity =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            he,
                            HotspotPosition.class
                    );
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                if (responseEntity.getBody() != null) {
                    HotspotPosition response = responseEntity.getBody();
                    return response;

                } else {
                    throw new ITNotFoundException();
                }
            } else {
                throw new ITNotFoundException();
            }
        } catch (HttpClientErrorException e) {
            log.error("Position backend communication exception :" + e.getStatusCode() + "[" + e.getMessage() + "]");
            log.error("Related to Hotspost details (1) for "+hotspotID);
            log.error("Url :"+url);
            throw new ITParseException();
        } catch (HttpServerErrorException e) {
            log.error("Position backend communication exception :" + e.getStatusCode() + "[" + e.getMessage() + "]");
            log.error("Related to Hotspost details (2) for "+hotspotID);
            throw new ITParseException();
        } catch (Exception x ) {
            log.error("Unexpected error "+x.getMessage());
            throw new ITParseException();
        }
    }

    public Hotspot.HotspotBrand loadHotspotBrand(String hotspotID) throws ITNotFoundException, ITParseException {

        RestTemplate restTemplate = new RestTemplate();
        String url="";
        try {
            HttpEntity<String> he;
            he = createHeaders(true);
            url = forwarderConfig.getHeliumBrandUrl();
            url = url.replace("{hs}",hotspotID);
            ResponseEntity<HotspotBrand> responseEntity =
                restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    he,
                    HotspotBrand.class
                );
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                if (responseEntity.getBody() != null) {
                    HotspotBrand response = responseEntity.getBody();
                    return response.getBrand();
                } else {
                    throw new ITNotFoundException();
                }
            } else {
                throw new ITNotFoundException();
            }
        } catch (HttpClientErrorException e) {
            log.error("Brand backend communication exception :" + e.getStatusCode() + "[" + e.getMessage() + "]");
            log.error("Related to Hotspost details (1) for "+hotspotID);
            log.error("Url :"+url);
            throw new ITParseException();
        } catch (HttpServerErrorException e) {
            log.error("Brand backend communication exception :" + e.getStatusCode() + "[" + e.getMessage() + "]");
            log.error("Related to Hotspost details (2) for " + hotspotID);
            throw new ITParseException();
        } catch ( ITNotFoundException x) {
            log.error("Brand Not Found");
            throw new ITNotFoundException();
        } catch (Exception x ) {
            log.error("Unexpected error "+x.getClass()+" / "+x.getMessage());
            throw new ITParseException();
        }
    }


}
