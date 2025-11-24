package com.smartexpense.app.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.smartexpense.app.R;
import com.smartexpense.app.model.Expense;
import com.smartexpense.app.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenses;
    private OnExpenseClickListener listener;

    public interface OnExpenseClickListener {
        void onExpenseClick(Expense expense);
        void onEditClick(Expense expense);
        void onDeleteClick(Expense expense);
    }

    public ExpenseAdapter(OnExpenseClickListener listener) {
        this.expenses = new ArrayList<>();
        this.listener = listener;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.bind(expense);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {

        private View categoryIndicator;
        private TextView descriptionText;
        private TextView categoryText;
        private TextView dateText;
        private TextView amountText;
        private View actionButtonsLayout;
        private MaterialButton editButton;
        private MaterialButton deleteButton;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryIndicator = itemView.findViewById(R.id.categoryIndicator);
            descriptionText = itemView.findViewById(R.id.descriptionText);
            categoryText = itemView.findViewById(R.id.categoryText);
            dateText = itemView.findViewById(R.id.dateText);
            amountText = itemView.findViewById(R.id.amountText);
            actionButtonsLayout = itemView.findViewById(R.id.actionButtonsLayout);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(Expense expense) {
            descriptionText.setText(expense.getDescription());
            categoryText.setText(expense.getCategory());
            dateText.setText(FormatUtils.formatDate(expense.getDate()));
            amountText.setText(FormatUtils.formatCurrency(expense.getAmount()));

            // Set category indicator color
            int color = getCategoryColor(expense.getCategory());
            categoryIndicator.setBackgroundColor(color);

            // Item click to expand/collapse actions
            itemView.setOnClickListener(v -> {
                if (actionButtonsLayout.getVisibility() == View.VISIBLE) {
                    actionButtonsLayout.setVisibility(View.GONE);
                } else {
                    actionButtonsLayout.setVisibility(View.VISIBLE);
                }
            });

            // Edit button
            editButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditClick(expense);
                }
            });

            // Delete button
            deleteButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(expense);
                }
            });
        }

        private int getCategoryColor(String category) {
            switch (category) {
                case "Food":
                    return Color.parseColor("#FF6384");
                case "Transport":
                    return Color.parseColor("#36A2EB");
                case "Shopping":
                    return Color.parseColor("#FFCE56");
                case "Bills":
                    return Color.parseColor("#4BC0C0");
                case "Other":
                default:
                    return Color.parseColor("#9966FF");
            }
        }
    }
}

