package com.example.catapp.common

sealed class AppState<T>(open val data: T?) {

    class Loading<T>(override val data: T? = null) : AppState<T>(data)
    data class Idle<T>(override val data: T?) : AppState<T>(data)
    data class Error<T>(override val data: T? = null, val error: Throwable) : AppState<T>(data)

    val isLoading: Boolean
        get() = this is Loading

    val isIdle: Boolean
        get() = this is Idle

    val isError: Boolean
        get() = this is Error


    /**
     * Map the current state to a new loading state
     * @return the new loading state
     */
    fun toLoading(): AppState<T> {
        return Loading(data)
    }

    /**
     * Map the current state to a new idle state
     * @return the new idle state
     */
    fun toIdle(): AppState<T> {
        return Idle(data)
    }

    /**
     * Map the current state to a new error state with the given [Throwable]
     * @param throwable [Throwable] specifying the error
     * @return the new error state
     */
    fun toError(throwable: Throwable): AppState<T> {
        return Error(data, throwable)
    }

    companion object {

        fun <T> loading(): AppState<T> {
            return Loading()
        }

        fun <T> loading(data: T?): AppState<T> {
            return Loading(data)
        }

        fun <T> idle(): AppState<T> {
            return Idle(null)
        }

        fun <T> idle(data: T?): AppState<T> {
            return Idle(data)
        }

        fun <T> error(data: T?, error: Throwable): AppState<T> {
            return Error(data, error)
        }

        fun <T> error(error: Throwable): AppState<T> {
            return Error(null, error)
        }
    }
}