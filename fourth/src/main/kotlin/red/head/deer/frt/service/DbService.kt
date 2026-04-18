import red.head.deer.frt.config.Config
class DbService(
  private val config: Config
) {
val insert = """
INSERT INTO test_result (step, status, result)
VALUES (:step, :status, :result)
""" 

val update = """
UPDATE test_result SET status=:status, result=:result
WHERE step=:step
""" 

val selectStatus = """
SELECT status FROM test_result WHERE step=:step
""" 

val selectResult = """
SELECT result FROM test_result WHERE step:=step
 """ 
}