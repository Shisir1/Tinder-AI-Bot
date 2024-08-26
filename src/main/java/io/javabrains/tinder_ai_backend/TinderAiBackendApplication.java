package io.javabrains.tinder_ai_backend;

import io.javabrains.tinder_ai_backend.conversations.ChatMessage;
import io.javabrains.tinder_ai_backend.conversations.Conversation;
import io.javabrains.tinder_ai_backend.conversations.ConversationRepository;
import io.javabrains.tinder_ai_backend.profiles.Gender;
import io.javabrains.tinder_ai_backend.profiles.Profile;
import io.javabrains.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ConversationRepository conversationRepository;
	@Autowired
	private OpenAiChatModel chatClient;
	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

	public void run(String... args){


		Prompt prompt = new Prompt("who is Shisir Humagain?");
		ChatResponse response = chatClient.call(prompt);
		System.out.println(response.getResult().getOutput());

		//to delete all the profiles and conversations prior to this run
		profileRepository.deleteAll();
		conversationRepository.deleteAll();

		//Profiles
		Profile profile = new Profile(
				"1",
				"Shisir",
				"Humagain",
				40,
				"Nepali",
				Gender.MALE,
				"Software Engineer",
				"foo.jpg",
				"INTP");
		profileRepository.save(profile);
		 Profile profile2 = new Profile(
				"2",
				"foo",
				"bar",
				40,
				"test",
				Gender.FEMALE,
				"Software Engineer",
				"foo.jpg",
				"INTP");
		 profileRepository.save(profile2);
		profileRepository.findAll().forEach(System.out::println);

		//Conversation
		Conversation conversation = new Conversation(
				"1",
				profile.id(),
				List.of(
						new ChatMessage("Hello",profile.id(), LocalDateTime.now())
				)
		);
		conversationRepository.save(conversation);
		conversationRepository.findAll().forEach(System.out::println);

	}

}
