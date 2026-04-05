package ru.anlyashenko.atmosphereapp.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event: UiEvent, State: UiState, Effect: UiEffect> : ViewModel() {
    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    val currentState: State
        get() = uiState.value

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    // TODO: Возможно, более надёжный вариант SharedFlow с replay = 1
    /**
     * Channel может потерять события - если UI не подписан в момент отправки эффекта
     */
    private val _effect: Channel<Effect> = Channel()
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    // TODO: Нет обработки ошибок
    /**
     * Если handleEvent бросит исключение, корутина упадёт и события перестанут обрабатываться
     */
    private fun subscribeEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvent(it)
            }
        }
    }

    abstract fun handleEvent(event: Event)

    // TODO: Возможно, MutableSharedFlow избыточен, можно попробовать >>>
    /*
    fun setEvent(event: Event) {
        viewModelScope.launch { handleEvent(event) }
    }
     */
    fun setEvent(event: Event) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    // TODO: Лучше использовать один общий scope
    /**
     * setEffect запускает корутину на каждый эффект, могут создаваться много корутин
     */
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

}
