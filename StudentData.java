package Automation.TestAutomation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.testng.annotations.Test;

public class StudentData {
	@Test
	public void studentTest1() throws IllegalArgumentException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		// Read JSON from file

		File file = new File(
				System.getProperty("user.dir") + "\\src\\test\\java\\Automation\\TestAutomation\\testData.json");
		JsonNode jsonNode = objectMapper.readTree(file);

		// Get the "students" array from JSON
		List<Student> students = new ArrayList<>();
		for (JsonNode studentNode : jsonNode.get("students")) {
			Student student = objectMapper.treeToValue(studentNode, Student.class);
			students.add(student);
		}

		// Sort students based on score and marks
		students.sort(Comparator.comparing(Student::getScore).reversed().thenComparing(s -> s.getMarks().get(0)));

		// Serialize sorted list back to JSON
		JsonNode sortedJsonNode = objectMapper.valueToTree(students);
		JsonNode sortedJson = objectMapper.createObjectNode().set("students", sortedJsonNode);

		// Print the sorted JSON
		System.out.println(sortedJson.toPrettyString());
	}
}
