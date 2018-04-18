package agh.po.projekt;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateTimeTest {

    private DateTime dt = new DateTime(15,4,2018,12,45);

    @Test
    public void testExceptionMessage(){
        try {
            new DateTime(30, 2, 2017);
        } catch (IllegalArgumentException e){
            assertEquals("Niepoprawna data lub czas.",e.getMessage());
        }
    }

    @Test
    public void getDate() {
        assertEquals("15-04-2018",dt.getDate());
    }

    @Test
    public void getTime() {
        assertEquals("12:45",dt.getTime());
    }

    @Test
    public void getYear() {
        assertEquals(2018,dt.getYear());
    }

    @Test
    public void getMonth() {
        assertEquals(4,dt.getMonth());
    }

    @Test
    public void getDay() {
        assertEquals(15,dt.getDay());
    }

    @Test
    public void getHour() {
        assertEquals(12,dt.getHour());
    }

    @Test
    public void getMinutes() {
        assertEquals(45,dt.getMinutes());
    }

    @Test
    public void equals() {
        assertTrue(dt.equals(new DateTime("15-04-2018","12:45")));
    }

    @Test
    public void dateEquals() {
        assertTrue(dt.dateEquals(new DateTime("15-04-2018")));
    }

    @Test
    public void dateIsCorrect() {
        assertTrue(dt.dateIsCorrect());
    }

    @Test
    public void timeIsCorrect() {
        assertTrue(dt.timeIsCorrect());
    }

}