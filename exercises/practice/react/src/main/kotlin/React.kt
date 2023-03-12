import kotlin.properties.Delegates

class Reactor<T>() {
    // Your compute cell's addCallback method must return an object
    // that implements the Subscription interface.
    interface Subscription {
        fun cancel()
    }

    open inner class InputCell(init: T) {
        val subscribers = mutableListOf<ComputeCell>()
        var value = init
            set(new) {
                field = new
                for (sub in subscribers) {sub.update()}
                if (this !is ComputeCell)
                    for (sub in subscribers) {sub.callback()}
            }

        fun subscribe(sub: ComputeCell) = subscribers.add(sub)
    }

    inner class ComputeCell(private vararg var cells: InputCell, var compute: ((List<T>) -> T)) : InputCell(compute(cells.map { it.value })) {
        init {
            cells.map { it.subscribe(this) }
        }
        inner class Callback(private var callback: ((T) -> Unit)) : Subscription {

            private var isActive = true

            override fun cancel() {
                isActive = false
            }

            operator fun invoke(value: T) {
                if (isActive)
                    callback(value)
            }
        }

        private val callbacks = mutableListOf<Callback>()

        fun addCallback(callback: ((T) -> Unit)): Callback {
            val callbackObject = Callback(callback)
            callbacks.add(callbackObject)
            return callbackObject
        }

        private var updateInProgress = false

        private var oldValue: T? = null

        fun update() {
            // Store the value before the update
            if (!updateInProgress) {
                oldValue = value
                updateInProgress = true
            }
            value = compute(cells.map { it.value })
        }

        fun callback() {
            if (updateInProgress) {
                updateInProgress = false
                if (oldValue != value) {
                    callbacks.forEach { it(value) }
                }
                // The callback of the subscribers should be called too
                subscribers.forEach { it.callback() }
            }
        }
    }
}

