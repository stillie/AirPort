package za.co.stillie.airport.ui.flight_schedule;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import za.co.stillie.airport.databinding.ItemFlightScheduleBinding;
import za.co.stillie.airport.service.models.FlightScheduleResponse;

public class FlightScheduleAdapter extends RecyclerView.Adapter<FlightScheduleAdapter.ViewHolder> {
    private final List<FlightScheduleResponse> mFlightList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup aViewGroup, int aI) {
        ItemFlightScheduleBinding binding = ItemFlightScheduleBinding.inflate(LayoutInflater.from(aViewGroup.getContext()), aViewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder aViewHolder, int position) {
        aViewHolder.bindView(mFlightList.get(position));
    }

    @Override
    public int getItemCount() {
        return mFlightList.size();
    }

    public void addItems(List<FlightScheduleResponse> items) {
        mFlightList.clear();
        mFlightList.addAll(items);
        notifyItemRangeChanged(0, mFlightList.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemFlightScheduleBinding mItemFlightScheduleBinding;

        public ViewHolder(@NonNull ItemFlightScheduleBinding itemView) {
            super(itemView.getRoot());
            mItemFlightScheduleBinding = itemView;
            mItemFlightScheduleBinding.executePendingBindings();
        }

        public void bindView(FlightScheduleResponse schedule) {
            mItemFlightScheduleBinding.setFlight(schedule);
        }
    }
}
