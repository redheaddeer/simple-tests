package red.head.deer.frt.service

import result
import steps

class ResultService {
    fun setResult() {
        val failed = steps.any { it.status == "FAIL" }
        result.status = if (failed) "FAILED" else "SUCCESS"
        result.results = steps
    }


}