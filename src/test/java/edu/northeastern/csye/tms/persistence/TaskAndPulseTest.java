package edu.northeastern.csye.tms.persistence;

import edu.northeastern.csye.tms.entity.Pulse;
import edu.northeastern.csye.tms.entity.Task;
import edu.northeastern.csye.tms.service.TaskPulseService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskAndPulseTest {

    @Autowired
    private TaskPulseService taskPulseService;

    private static int testTaskId;
    private static int testTask2Id;

    private static int testPulseId;


    @BeforeAll
    public static void setup(@Autowired TaskPulseService taskPulseService){

        Task task = new Task("todo", "test", "pending", 1);

        taskPulseService.persistTask(task);
        testTaskId = task.getId();
    }

    @Test
    @Order(1)
    public void persistTask(){
        Task task = new Task("todo", "test2", "complete", 2);

        taskPulseService.persistTask(task);
        testTask2Id = task.getId();

        Assertions.assertEquals(task.getName(), taskPulseService.getTask(testTask2Id).getName());
    }

    @Test
    @Order(2)
    public void getTasks(){
        List<Task> tasks = taskPulseService.getTasks();
        List<String> taskNames = new ArrayList<>();
        tasks.forEach(task -> taskNames.add(task.getName()));

        Assertions.assertArrayEquals(new String[]{"test", "test2"}, taskNames.toArray());
    }

    @Test
    @Order(3)
    public void getTask(){
        Task task = taskPulseService.getTask(testTaskId);

        Assertions.assertEquals("test", task.getName());
    }

    @Test
    @Order(5)
    public void updateTask(){
        Task testTask = taskPulseService.getTask(testTaskId);
        testTask.setName("my_task");
        taskPulseService.updateTask(testTask);

        testTask = taskPulseService.getTask(testTaskId);

        Assertions.assertEquals("my_task", testTask.getName());
    }

    @Test
    @Order(5)
    public void deleteTask(){
        taskPulseService.deleteTask(testTaskId);
        taskPulseService.deleteTask(testTask2Id);

        Assertions.assertEquals(0, taskPulseService.getTasks().size());
    }

    @Test
    @Order(6)
    public void persistPulse(){
        Pulse pulse = new Pulse("comment for task");
        taskPulseService.persistPulse(pulse);
        testPulseId = pulse.getId();

        Assertions.assertEquals(pulse.getComment(), taskPulseService.getPulse(testPulseId).getComment());
    }

    @Test
    @Order(7)
    public void updatePulse(){

        Pulse pulse = taskPulseService.getPulse(testPulseId);
        pulse.setComment("updated comment");
        taskPulseService.updatePulse(pulse);

        Assertions.assertEquals(pulse.getComment(), taskPulseService.getPulse(testPulseId).getComment());
    }

    @Test
    @Order(8)
    public void getPulses(){

        List<Pulse> pulses = taskPulseService.getPulses();
        List<String> pulseComments = new ArrayList<>();
        pulses.forEach(pulse -> {
            pulseComments.add(pulse.getComment());
        });

        Assertions.assertArrayEquals(new String[]{"updated comment"}, pulseComments.toArray());
    }

    @Test
    @Order(9)
    public void deletePulse(){

        taskPulseService.deletePulse(testPulseId);

        Assertions.assertEquals(0, taskPulseService.getPulses().size());
    }
}