package ntu.edu.nguyenquockhanh.btl_quizzing.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ntu.edu.nguyenquockhanh.btl_quizzing.R;
import ntu.edu.nguyenquockhanh.btl_quizzing.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categoryList;
    private Context context;

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category c = categoryList.get(position);

        holder.tvName.setText(c.getName());
        holder.imgIcon.setImageResource(c.getIconResId());

        // Tạo gradient background
        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{c.getStartColor(), c.getEndColor()}
        );
        gradient.setCornerRadius(20f);
        holder.container.setBackground(gradient);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public CategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView tvName;
        ConstraintLayout container;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgIcon = itemView.findViewById(R.id.imageView);
            this.tvName = itemView.findViewById(R.id.tv_name);
            this.container = itemView.findViewById(R.id.category_container);
        }
    }
}
