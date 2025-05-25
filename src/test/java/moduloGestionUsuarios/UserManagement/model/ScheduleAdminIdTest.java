package moduloGestionUsuarios.UserManagement.model;

import moduloGestionUsuarios.UserManagement.model.ScheduleAdminId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScheduleAdminIdTest {

    @Test
    void testEquals_sameValues_shouldReturnTrue() {
        ScheduleAdminId id1 = new ScheduleAdminId();
        ScheduleAdminId id2 = new ScheduleAdminId();
        id1.setId_admin("admin1");
        id1.setId_schedule("schedule1");
        id2.setId_admin("admin1");
        id2.setId_schedule("schedule1");

        assertEquals(id1, id2);
    }

    @Test
    void testEquals_differentAdmin_shouldReturnFalse() {
        ScheduleAdminId id1 = new ScheduleAdminId();
        ScheduleAdminId id2 = new ScheduleAdminId();
        id1.setId_admin("admin1");
        id1.setId_schedule("schedule1");
        id2.setId_admin("admin2");
        id2.setId_schedule("schedule1");

        assertNotEquals(id1, id2);
    }

    @Test
    void testEquals_differentSchedule_shouldReturnFalse() {
        ScheduleAdminId id1 = new ScheduleAdminId();
        ScheduleAdminId id2 = new ScheduleAdminId();
        id1.setId_admin("admin1");
        id1.setId_schedule("schedule1");
        id2.setId_admin("admin1");
        id2.setId_schedule("schedule2");

        assertNotEquals(id1, id2);
    }

    @Test
    void testEquals_nullOrOtherClass_shouldReturnFalse() {
        ScheduleAdminId id = new ScheduleAdminId();
        id.setId_admin("admin");
        id.setId_schedule("schedule");

        assertNotEquals(id, null);
        assertNotEquals(id, "some string");
    }

    @Test
    void testHashCode_sameValues_shouldBeEqual() {
        ScheduleAdminId id1 = new ScheduleAdminId();
        ScheduleAdminId id2 = new ScheduleAdminId();
        id1.setId_admin("admin1");
        id1.setId_schedule("schedule1");
        id2.setId_admin("admin1");
        id2.setId_schedule("schedule1");

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    void testHashCode_differentValues_shouldNotBeEqual() {
        ScheduleAdminId id1 = new ScheduleAdminId();
        ScheduleAdminId id2 = new ScheduleAdminId();
        id1.setId_admin("admin1");
        id1.setId_schedule("schedule1");
        id2.setId_admin("admin2");
        id2.setId_schedule("schedule2");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }
}