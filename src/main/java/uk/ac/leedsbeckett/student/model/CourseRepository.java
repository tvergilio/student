package uk.ac.leedsbeckett.student.model;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Inherit database interaction functionality from JpaRepository for Course class, of ID type Long
 * Create new Course *
 * Update existing Course *
 * Delete Course *
 * Find Course (one, all, or search by simple or complex properties)
 */
public interface CourseRepository extends JpaRepository<Course, Long> {
}
