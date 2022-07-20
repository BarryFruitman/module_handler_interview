package com.scribd.app.discover_modules.featured_document;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.scribd.app.discover_modules.BasicDiscoverModuleWithMetadata;
import com.scribd.app.discover_modules.BasicDiscoverModuleWithMetadataFactory;
import com.scribd.app.discover_modules.DiscoverModule;
import com.scribd.app.discover_modules.DiscoverModuleWithMetadata;
import com.scribd.app.discover_modules.ModuleDelegate;
import com.scribd.app.discover_modules.ModuleHandler;
import com.scribd.app.discover_modules.ModuleViewHolder;
import com.scribd.dataia.Document;
import com.scribd.presentation.AdapterWithAnalytics;
import com.scribd.presentation.thumbnail.ThumbnailView;
import com.scribd.presentationia.thumbnail.ThumbnailViewModel;

public class FeaturedDocumentModuleHandler
        extends ModuleHandler<BasicDiscoverModuleWithMetadata, FeaturedDocumentModuleHandler.FeaturedDocumentViewHolder> {

    private static final String TAG = "FeaturedDocumentModuleHandler";

    private ThumbnailViewModel thumbnailViewModel;

    public FeaturedDocumentModuleHandler(Fragment fragment, ModuleDelegate moduleInterface) {
        super(fragment, moduleInterface);
    }

    @Override
    public void handleView(BasicDiscoverModuleWithMetadata module, FeaturedDocumentViewHolder holder, int position, AdapterWithAnalytics parentAdapter) {
        Document document = module.getDataObject().getDocuments()[0];
        Document.EditorialBlurb editorialBlurb = document.getEditorialBlurb();

        thumbnailViewModel = new ViewModelProvider(getFragment()).get(ThumbnailViewModel.class);

        holder.headerTitle.setText(editorialBlurb.getHeader());
        holder.editorialBlurbQuoteText.setText(editorialBlurb.getTitle());
        holder.editorialBlurbParagraphText.setText(editorialBlurb.getDescription());
        holder.setupThumbnail(document.getServerId(), document.getDocumentType());
        holder.thumbnail.setOnLongClickListener((v, docId) -> thumbnailViewModel.onThumbnailLongClick(docId));

        setupOnClickListener(document, holder, module, holder.itemView);
        setupOnClickListener(document, holder, module, holder.thumbnail);
    }

    @Override
    public void onViewRecycled(@NonNull FeaturedDocumentViewHolder holder) {
        super.onViewRecycled(holder);
        holder.resetThumbnail(holder.getItemId());
    }

    @VisibleForTesting
    protected void setupOnClickListener(final Document document, final FeaturedDocumentViewHolder holder,
                                        final BasicDiscoverModuleWithMetadata basicModule,
                                        View view) {
        view.setOnClickListener(v -> {
            // STUBBED
        });
    }

    @Override
    public FeaturedDocumentViewHolder createViewHolder(View itemView) {
        return new FeaturedDocumentViewHolder(itemView);
    }

    @Override
    public boolean canHandle(@NonNull DiscoverModule discoverModule) {
        return DiscoverModule.Type.featured_document.name().equals(discoverModule.getType());
    }

    @Override
    public boolean isDataValid(@NonNull DiscoverModule discoverModule) {
        if (discoverModule.getDocuments() == null || discoverModule.getDocuments().length < 1) {
            return false;
        }

        Document document = discoverModule.getDocuments()[0];
        return document != null && document.getEditorialBlurb() != null && !TextUtils.isEmpty(document.getEditorialBlurb().getHeader()) &&
                !TextUtils.isEmpty(document.getEditorialBlurb().getDescription()) && !TextUtils.isEmpty(document.getEditorialBlurb()
                .getTitle());
    }

    @Override
    public BasicDiscoverModuleWithMetadata createDiscoverModuleWithMetadata(DiscoverModule discoverModule,
                                                                            DiscoverModuleWithMetadata.ModuleMetadata metadata) {
        return new BasicDiscoverModuleWithMetadataFactory(this, discoverModule, metadata).createSingleDocumentModule();
    }


    @Override
    public int getLayoutId() {
        return R.layout.module_featured_document;
    }

    @VisibleForTesting
    static class FeaturedDocumentViewHolder extends ModuleViewHolder {
        public ThumbnailView thumbnail;
        public final TextView headerTitle;
        public final TextView editorialBlurbQuoteText;
        public final TextView editorialBlurbParagraphText;
        private ThumbnailDelegate thumbnailDelegate;

        public FeaturedDocumentViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            headerTitle = itemView.findViewById(R.id.headerTitle);
            editorialBlurbQuoteText = itemView.findViewById(R.id.editorialBlurbQuoteText);
            editorialBlurbParagraphText = itemView.findViewById(R.id.editorialBlurbParagraphText);
        }

        private void setupThumbnail(int docId, String docType) {
            thumbnailDelegate.setupThumbnailSize(docType, this);
            thumbnailDelegate.loadThumbnail(docId, thumbnail, this);
        }

        private void resetThumbnail(long holderItemId) {
            thumbnailDelegate.resetThumbnail(thumbnail, this);
        }

        interface ThumbnailDelegate {
            void setupThumbnailSize(String docType, FeaturedDocumentViewHolder holder);
            void loadThumbnail(int docId, ThumbnailView view, FeaturedDocumentViewHolder holder);
            void resetThumbnail(ThumbnailView view, FeaturedDocumentViewHolder holder);
        }
    }
}