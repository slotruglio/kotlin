class BankAccount {
    // TODO: implement read access to 'balance'
    private var open = true
    var balance: Long = 0
        get() { check(open); return field }
        private set

    fun adjustBalance(amount: Long){
        // TODO("Implement the function to complete the task")
        synchronized(this){ balance += amount }
    }

    fun close() {
        // TODO("Implement the function to complete the task")
        open = false
    }
}
