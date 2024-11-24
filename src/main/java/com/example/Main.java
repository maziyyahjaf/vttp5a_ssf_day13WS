package com.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

	private static String dataDir;

	// inject property value from application.properties
	@Value("${data.dir}")
	private String defaultDataDir;

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(Main.class);

		app.run(args);
	}

	// Bean to provide dataDir to the Spring context
	@Bean
	public String dataDir(ApplicationArguments args) {
		if (args.containsOption("dataDir")) {
			dataDir = args.getOptionValues("dataDir").get(0);
		} else {
			dataDir = defaultDataDir;
		}

		// provide a default

		return dataDir;

	}

	@Bean
	public CommandLineRunner handleDirectory() {
		return  args -> {

			// DefaultApplicationArguments cliOpts = new DefaultApplicationArguments(args);

			// check if --dataDir option was provided
			// if (cliOpts.containsOption("dataDir")) {
			// // get the value of the dataDir argument
			// dataDir = cliOpts.getOptionValues("dataDir").get(0);

			// Validate and handle the directory

			Path dirPath = Paths.get(dataDir);

			try {
				if (Files.exists(dirPath)) {
					if (!Files.isDirectory(dirPath)) {
						System.out.println("Error: The path provided is not a directory");
						System.exit(1); // exit if not a directory
					}
				} else {
					// Directory does not exist; create it
					Files.createDirectories(dirPath);
					System.out.println("Directory created at: " + dirPath);
				}
			} catch (Exception e) {
				System.out.println("Error handling directory: " + e.getMessage());
				System.exit(1); // exit on error
			}

		};
	}

}
