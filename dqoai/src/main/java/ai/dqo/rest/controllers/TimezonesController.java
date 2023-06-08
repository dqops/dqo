/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.rest.controllers;

import ai.dqo.rest.models.platform.SpringErrorPayload;
import ai.dqo.utils.datetime.TimeZoneUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * REST api controller to return a list of available time zones.
 */
@RestController
@RequestMapping("/api/timezones")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Timezones", description = "Timezone management")

public class TimezonesController {

    @GetMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "getAvailableZoneIds", notes = "Returns a list of available time zone ids", response = String[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<List<String>>> getAvailableZoneIds() {
        return new ResponseEntity<>(Mono.just(TimeZoneUtility.getAvailableZoneIds()), HttpStatus.OK);
    }
}
