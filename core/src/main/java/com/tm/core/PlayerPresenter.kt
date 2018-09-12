package com.tm.core

class PlayerPresenter(val view: PlayerView) {

    fun play() {
        view.show("Hello Core")
    }
}