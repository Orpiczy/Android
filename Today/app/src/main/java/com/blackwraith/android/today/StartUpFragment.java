package com.blackwraith.android.today;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StartUpFragment extends Fragment {
    private static final String TAG = "Activity";
    private RecyclerView mTaskRecyclerView;
    private TaskToDoAdapter mAdapter;

    private Callbacks mCallbacks;
    ItemTouchHelper.SimpleCallback SwipedLeft = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            TaskToDo completedTask = TaskToDoList.get(getActivity()).getTasks().get(viewHolder.getAdapterPosition());
            mCallbacks.onTaskDeleted(completedTask);
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Wywołanie metody: OncreateView w StartUpFragment");

        View view = inflater.inflate(R.layout.fragment_start_up, container, false);
        mTaskRecyclerView = (RecyclerView) view.findViewById(R.id.task_recycler_view);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        new ItemTouchHelper(SwipedLeft).attachToRecyclerView(mTaskRecyclerView);

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.start_up_fragment, menu);
        //miejsce na dodatkowy kod wyświetlający np dodatkowe informacje na pasku menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_task:
                TaskToDo task = new TaskToDo();
                TaskToDoList.get(getActivity()).addTask(task);
                mCallbacks.onTaskSelected(task);
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    public void updateUI() {
        Log.d(TAG, "Wywołanie metody: updateUI() w StartUpFragment");
        TaskToDoList taskToDoList = TaskToDoList.get(getActivity());
        List<TaskToDo> tasksToDo = taskToDoList.getTasks();
        if (mAdapter == null) {
            mAdapter = new TaskToDoAdapter(tasksToDo);
            mTaskRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTasksToDo(tasksToDo);
            mAdapter.notifyDataSetChanged();
        }
    }


    public interface Callbacks {
        void onTaskSelected(TaskToDo task);

        void onTaskCompleted(TaskToDo task);

        void onTaskDeleted(TaskToDo task);
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDeadlineTextView;
        private ProgressBar mProgressBar;
        private TaskToDo mTaskToDo;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_general_tasks, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.task_name);
            mDeadlineTextView = (TextView) itemView.findViewById(R.id.task_deadline);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.task_progress_bar);

        }

        public void bind(TaskToDo taskToDo) {
            Log.d(TAG, "Wywołanie metody: bind w StartUpFargment");
            mTaskToDo = taskToDo;
            mTitleTextView.setText(mTaskToDo.getTitle());
//            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
//            mDeadlineTextView.setText(dateFormat.format(mTaskToDo.getDeadlineDate()));
            mDeadlineTextView.setText(mTaskToDo.getDeadlineDate().format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss ")));

        }

        @Override
        public void onClick(View view) {
            mCallbacks.onTaskSelected(mTaskToDo);
        }
    }

    private class TaskToDoAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<TaskToDo> mTaskToDos;

        public TaskToDoAdapter(List<TaskToDo> taskToDos) {
            Log.d(TAG, "Wywołanie metody: konstruktor TaskToDoAdapter w TaskToDoAdapterListFargment");
            mTaskToDos = taskToDos;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            TaskToDo taskToDo = mTaskToDos.get(position);
            holder.bind(taskToDo);
        }

        @Override
        public int getItemCount() {
            return mTaskToDos.size();
        }

        public void setTasksToDo(List<TaskToDo> taskToDos) {
            mTaskToDos = taskToDos;
        }
    }

}
