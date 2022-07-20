package com.scribd.app.discover_modules;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.scribd.presentation.AdapterWithAnalytics;

import java.util.ArrayList;
import java.util.List;

public abstract class ModuleHandler<VM extends DiscoverModuleWithMetadata<?>, VH extends ModuleViewHolder> {
    private static final String TAG = "ModuleHandler";

    protected ModuleDelegate moduleDelegate;
    private Fragment fragment;

    public abstract void handleView(VM module, VH holder, int position, AdapterWithAnalytics parentAdapter);

    public abstract int getLayoutId();

    public ModuleHandler(@NonNull Fragment fragment, @NonNull ModuleDelegate moduleDelegate) {
        this.fragment = fragment;
        this.moduleDelegate = moduleDelegate;
    }

    protected ArrayList<String> recommendationIdsToLog;

    /**
     * Override this method if you need to reinitialize the view when it's recycled
     */
    public void onViewRecycled(@NonNull VH holder) {
    }

    public Fragment getFragment() {
        return fragment;
    }

    public View inflateView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
    }

    public abstract VH createViewHolder(@NonNull View itemView);

    public abstract boolean canHandle(@NonNull DiscoverModule discoverModule);

    public abstract boolean isDataValid(@NonNull DiscoverModule discoverModule);

    public abstract VM createDiscoverModuleWithMetadata(DiscoverModule discoverModule,
                                                        DiscoverModuleWithMetadata.ModuleMetadata metadata);

//    /**
//     * To sanitize individual items within a discoverModule, override this method and call {@link #sanitizeItems(DiscoverModule, Object[], ItemValidator)}
//     *
//     * @param discoverModule to be sanitized
//     */
//    public void sanitizeData(@NonNull DiscoverModule discoverModule) {
//    }
//
//    /**
//     * Builds and returns a list of objects of type {@code S}
//     * by using the provided {@link ItemValidator} as a filter
//     * for the given list of items
//     *
//     * Note: This method does not alter the contents
//     * of the supplied discoverModule or the items. So, you will
//     * most likely have to call a setter on {@code discoverModule}
//     * to set the validated items
//     *
//     * @param discoverModule the discoverModule being validated
//     * @param items          items in the discoverModule
//     * @param validator      callback for validating individual items
//     * @return List of valid items. May be empty but never null
//     */
//    @NonNull
//    protected final <S> List<S> sanitizeItems(@NonNull DiscoverModule discoverModule, S[] items, @NonNull ItemValidator<S> validator) {
//        if (items == null) {
//            // no need to log here because discoverModule itself will fail validation
//            return new ArrayList<>();
//        }
//
//        List<S> validItems = new ArrayList<>();
//        for (S item : items) {
//            if (item != null && validator.isValid(item)) {
//                validItems.add(item);
//            } else {
//                Logger.nonfatal(TAG,
//                        String.format("Invalid item in discoverModule type: %s item type: %s", discoverModule.getType(),
//                                items.getClass().getSimpleName()));
//                Analytics.Discover.logModuleItemRenderFailed(discoverModule, items.getClass().getSimpleName());
//            }
//        }
//
//        return validItems;
//    }

    /**
     * Interface definition for validating an item. A
     * common use of this interface is as an argument
     * for {@link #sanitizeItems(DiscoverModule, Object[], ItemValidator)}
     *
     * @param <R> the type of object that has to be validated
     */
    protected interface ItemValidator<R> {
        /**
         * Indicates whether the item is valid
         *
         * @param item the item to be validated
         * @return true if the item is valid, false otherwise
         */
        boolean isValid(@NonNull R item);
    }

    /**
     * This method and {@link #areContentsTheSame(DiscoverModuleWithMetadata, DiscoverModuleWithMetadata)}
     * are for checking if two modules that share the same handler are the same. Used with DiffUtil, they
     * can greatly optimize module UI changes/refreshes.
     *
     * On the other hand, if you use DiffUtil but want to manually handle UI changes yourself for a particular
     * module type, override these two methods to return true for that module type.
     *
     * This method is expected to execute before {@link #areContentsTheSame(DiscoverModuleWithMetadata, DiscoverModuleWithMetadata)}.
     * Also, {@link #areContentsTheSame(DiscoverModuleWithMetadata, DiscoverModuleWithMetadata)} is ONLY executed
     * if this method returns true.
     *
     * @param oldDiscoverModuleWithMetadata old discover module (with metadata)
     * @param newDiscoverModuleWithMetadata new discover module (with metadata)
     * @return true if items are the same, false otherwise
     */
    public boolean areItemsTheSame(@NonNull VM oldDiscoverModuleWithMetadata,
                                   @NonNull VM newDiscoverModuleWithMetadata) {
        return false;
    }

    /**
     * This method and {@link #areItemsTheSame(DiscoverModuleWithMetadata, DiscoverModuleWithMetadata)}
     * are for checking if two modules that share the same handler are the same. Used with DiffUtil, they
     * can greatly optimize module UI changes/refreshes.
     *
     * On the other hand, if you use DiffUtil but want to manually handle UI changes yourself for a particular
     * module type, override these two methods to return true for that module type.
     *
     * @param oldDiscoverModuleWithMetadata old discover module (with metadata)
     * @param newDiscoverModuleWithMetadata new discover module (with metadata)
     * @return true if the contents of the items are the same, false otherwise
     */
    public boolean areContentsTheSame(@NonNull VM oldDiscoverModuleWithMetadata,
                                      @NonNull VM newDiscoverModuleWithMetadata) {
        return false;
    }

}