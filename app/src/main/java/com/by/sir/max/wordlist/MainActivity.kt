package com.by.sir.max.wordlist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.by.sir.max.wordlist.activities.AddWordActivity
import com.by.sir.max.wordlist.database.WordDatabase
import com.by.sir.max.wordlist.databinding.ActivityMainBinding
import com.by.sir.max.wordlist.recyclerview.NotifyGesture
import com.by.sir.max.wordlist.recyclerview.adapter.WordAdapter
import com.by.sir.max.wordlist.repository.Repository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_list_item.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: Repository
    private lateinit var viewModel: MainViewModel
    private lateinit var database: WordDatabase
    private lateinit var adapter: WordAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = WordDatabase.getInstance(this)
        repository = Repository(database)
        viewModel = MainViewModel(repository)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        adapter = WordAdapter()
        binding.apply {
            mainRecyclerView.adapter = adapter
            mainRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

            val swipe = object : NotifyGesture() {

                @SuppressLint("NotifyDataSetChanged")
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    super.onSwiped(viewHolder, direction)
                    when (direction) {
                        ItemTouchHelper.LEFT -> {
                            Snackbar.make(
                                viewHolder.itemView,
                                "Archived",
                                Snackbar.LENGTH_INDEFINITE
                            )
                                .setDuration(2000)
                                .setAction("Undo") {


                                }
                                .show()


                            lifecycleScope.launch {
                                delay(100L)
                                adapter.notifyDataSetChanged()
                            }
                        }
                        ItemTouchHelper.RIGHT -> {

                            viewHolder.itemView.item_text.text?.toString()
                                ?.let {
                                    viewModel.deleteWord(it)

                                    Snackbar.make(
                                        viewHolder.itemView,
                                        "Deleted",
                                        Snackbar.LENGTH_INDEFINITE
                                    )
                                        .setDuration(5000)
                                        .setAction("Undo") { view ->
                                            viewModel.addWord(it)
                                            view.visibility = View.INVISIBLE
                                            viewModel.getWords()
                                        }
                                        .show()
                                }
                            viewModel.getWords()
                            adapter.notifyDataSetChanged()
                            notifyChange()

                        }
                    }
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipe)
            itemTouchHelper.attachToRecyclerView(mainRecyclerView)
            floatingActionButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddWordActivity::class.java))
            }
            swipeToRefreshMainLayout.setOnRefreshListener {
                viewModel.getWords()
                swipeToRefreshMainLayout.isRefreshing = false
            }
        }

        viewModel.list.observe(this) { list ->
            adapter.submitList(list)
            adapter.notifyDataSetChanged()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter_sorted_by_alphabet -> {
                viewModel.getSortedWords()
            }
            R.id.filter_reversed_sort_words -> {
                viewModel.getReversedWords()
            }
            R.id.get_first_three_by_alpha -> {
                viewModel.getFirstWords(3)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_filter_words_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        viewModel.getWords()
        adapter.notifyDataSetChanged()
    }
}