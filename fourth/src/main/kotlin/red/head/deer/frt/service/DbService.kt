package red.head.deer.frt.service

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import red.head.deer.frt.config.Config

@Service
class DbService(
    private val jdbcTemplate: JdbcTemplate
) {
    private val insert = """
        INSERT INTO test_result (step, status, result)
        VALUES (:step, :status, :result)
    """

    private val update = """
        UPDATE test_result SET status=:status, result=:result
        WHERE step=:step
    """

    private val selectStatus = """
        SELECT status FROM test_result 
        WHERE step = :step
    """

    private val selectResult = """
        SELECT result FROM test_result 
        WHERE step = :step
    """

    fun insert(step: String, status: String, result: String) {
        jdbcTemplate.update(insert, step, status, result)
    }

    fun update(step: String, status: String, result: String) {
        jdbcTemplate.update(update, status, result, step)
    }

    fun selectStatus(step: String): String {
        return jdbcTemplate.queryForObject(selectStatus, String::class.java, step) ?: ""
    }

    fun selectResult(step: String): String {
        return jdbcTemplate.queryForObject(selectResult, String::class.java, step) ?: ""
    }
}