package com.pinal.my;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    Context context;
    List<UserModel> userModelList;
    private int gridSize = 5; // Define the size of the grid
    private OnItemClickListener onItemClickListener;
    private int clickedPosition = RecyclerView.NO_POSITION;

    public UserAdapter(Context context, List<UserModel> userModelList) {
        this.context = context;
        this.userModelList = userModelList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        UserModel userModel = userModelList.get(position);
        int iii = position + 1;
        holder.txtBullet.setText(String.valueOf(iii));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle item click
                clickedPosition = position;
                handleItemClick(position);
            }
        });

//        if (isPositionInSquare(position, clickedPosition)) {
//            if (position == clickedPosition){
//                holder.rel_rec.setBackgroundColor(Color.TRANSPARENT);
//            }else {
//                holder.rel_rec.setBackgroundColor(Color.RED);
//            }
//        }else if(position == clickedPosition){
//            holder.rel_rec.setBackgroundColor(Color.TRANSPARENT);
//        }else {
//            holder.rel_rec.setBackgroundColor(Color.TRANSPARENT);
//        }
        if (userModel.isHighlighted()) {
            holder.rel_rec.setBackgroundColor(Color.RED);
        } else {
            holder.rel_rec.setBackgroundColor(Color.TRANSPARENT);
        }

    }

    private boolean isPositionInSquare(int position, int centerPosition) {
        int clickedRow = centerPosition / gridSize;
        int clickedCol = centerPosition % gridSize;

        int currentRow = position / gridSize;
        int currentCol = position % gridSize;

        return currentRow >= clickedRow - 1 && currentRow <= clickedRow + 1
                && currentCol >= clickedCol - 1 && currentCol <= clickedCol + 1;
    }


    private void handleItemClick(int clickedPosition) {
        clearHighlighting();
        userModelList.get(clickedPosition).setHighlighted(true);
        highlightSquareAround(clickedPosition);
        notifyDataSetChanged();

        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(clickedPosition);
        }
    }


//    private void highlightSquareAround(int clickedPosition) {
//        int row = clickedPosition / gridSize;
//        int col = clickedPosition % gridSize;
//
//        int[] positionsToHighlight = {
//                clickedPosition - gridSize - 1, clickedPosition - gridSize, clickedPosition - gridSize + 1,
//                clickedPosition - 1, clickedPosition + 1,
//                clickedPosition + gridSize - 1, clickedPosition + gridSize, clickedPosition + gridSize + 1
//        };
//
//        for (int position : positionsToHighlight) {
//            if (position >= 0 && position < userModelList.size()) {
//                if (position == clickedPosition) {
//                    userModelList.get(position).setHighlighted(false);
//                } else {
//                    userModelList.get(position).setHighlighted(true);
//                }
//            }
//        }
//    }



    //horizontally and vertically
    private void highlightSquareAround(int clickedPosition) {
        int row = clickedPosition / gridSize;
        int col = clickedPosition % gridSize;

        // Highlight horizontally
        for (int i = row * gridSize; i < (row + 1) * gridSize; i++) {
            if (i != clickedPosition && i >= 0 && i < userModelList.size()) {
                userModelList.get(i).setHighlighted(true);
            } else {
                userModelList.get(i).setHighlighted(false);
            }
        }

        // Highlight vertically
        for (int i = col; i < userModelList.size(); i += gridSize) {
            if (i != clickedPosition) {
                userModelList.get(i).setHighlighted(true);
            } else {
                userModelList.get(i).setHighlighted(false);
            }
        }
    }


    private void clearHighlighting() {
        for (UserModel userModel : userModelList) {
            userModel.setHighlighted(false);
        }
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtBullet;
        RelativeLayout rel_rec;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBullet = itemView.findViewById(R.id.txtBullet);
            rel_rec = itemView.findViewById(R.id.rel_rec);
        }
    }
}
