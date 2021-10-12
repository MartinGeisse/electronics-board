package name.martingeisse.electronics_board.backend.platform;

import jakarta.servlet.DispatcherType;
import name.martingeisse.electronics_board.backend.SystemConfiguration;
import name.martingeisse.electronics_board.backend.application.util.PostHocInjector;
import name.martingeisse.electronics_board.backend.application.util.WebSocketEndpoints;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.EnumSet;

/**
 *
 */
public final class Launcher {

	// prevent instantiation
	private Launcher() {
	}

	public static void launch() throws Exception {

		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addFilterWithMapping(CorsFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
		ServletHolder servletHolder = servletHandler.addServletWithMapping(ServletContainer.class, "/*");
		servletHolder.setInitParameter("jakarta.ws.rs.Application", JerseyApplication.class.getName());

		ServletContextHandler contextHandler = new ServletContextHandler();
		contextHandler.setContextPath("/");
		contextHandler.setServletHandler(servletHandler);
		contextHandler.setInitParameter("logfilePath", "logs");

		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setName("server");
		Server server = new Server(threadPool);
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(SystemConfiguration.BACKEND_PORT);
		server.addConnector(connector);
		server.setHandler(contextHandler);

		JettyWebSocketServletContainerInitializer.configure(contextHandler, (servletContext, webSocketContainer) -> {
			webSocketContainer.setMaxTextMessageSize(65535);
			WebSocketEndpoints.defineEndpoints((pathSpec, adapterFactory) -> {
				webSocketContainer.addMapping(pathSpec, (request, response) -> {
					WebSocketAdapter adapter = adapterFactory.createWebSocketAdapter();
					if (PostHocInjector.INSTANCE_HOLDER.getValue() == null) {
						throw new RuntimeException("application not yet ready for web sockets");
					}
					PostHocInjector.INSTANCE_HOLDER.getValue().inject(adapter);
					return adapter;
				});
			});
		});

		server.start();
		server.join();

	}

}
