package ai.dqo.rest.controllers;

import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.ConnectionModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

/**
 * REST api controller to return a list of connections.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Connections", description = "Connection management")
public class ConnectionsController {
    private UserHomeContextFactory userHomeContextFactory;

    @Autowired
    public ConnectionsController(UserHomeContextFactory userHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of connections.
     * @return List of connections.
     */
    @GetMapping
    @ApiOperation(value = "getAllConnections", notes = "Returns a list of connection")
    public Flux<ConnectionModel> getAllConnections() {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        Stream<ConnectionModel> modelStream = connections.toList().stream().map(cw -> new ConnectionModel() {{
            setName(cw.getName());
            setSpec(cw.getSpec());
        }});

        return Flux.fromStream(modelStream);
    }
}
