package com.tm.tvstreams

import android.support.v7.view.ActionMode
import android.view.Menu
import android.view.MenuItem

internal object EditActionModeCallbacks {

    fun build(presenter: ChannelListPresenter): ActionMode.Callback {
        return object : ActionMode.Callback {

            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                menu.add("Select Channel to Edit")
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                mode.finish()
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                presenter.setNormalMode()
            }
        }
    }
}