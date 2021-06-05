package org.safegyn.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api
@RestController
public class MiscellaneousController {

    private final Logger projectLogger = Logger.getLogger("org.safegyn");
    private static final Logger logger = Logger.getLogger(MiscellaneousController.class);

    @ApiOperation(value = "Get robots.txt")
    @RequestMapping(path = "/robots.txt", method = RequestMethod.GET)
    public void robots(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().write("# www.robotstxt.org/\n\nUser-agent: *\nDisallow: \n");
        } catch (IOException e) {
            logger.error("Failed to serve robots.txt due to \n" + e.getMessage());
        }
    }

    @ApiOperation(value = "Change logging level")
    @RequestMapping(path = "/api/log-level", method = RequestMethod.PUT)
    public ResponseEntity<Object> changeLogLevel(@RequestParam Level level) {
        projectLogger.setLevel(level);
        logger.error("THIS IS AN ERROR");
        logger.info("THIS IS AN INFORMATION");
        return new ResponseEntity<>("Changed log-level to " + level.toString(), HttpStatus.OK);
    }

}