package com.scribd.app.discover_modules.article;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.scribd.app.DocumentLauncher;
import com.scribd.app.discover_modules.BasicDiscoverModuleWithMetadata;
import com.scribd.app.discover_modules.BasicDiscoverModuleWithMetadataFactory;
import com.scribd.app.discover_modules.DiscoverModule;
import com.scribd.app.discover_modules.DiscoverModuleWithMetadata;
import com.scribd.app.discover_modules.ModuleDelegate;
import com.scribd.app.discover_modules.ModuleHandler;
import com.scribd.app.ui.ArticleListItemView;
import com.scribd.dataia.Analytics;
import com.scribd.dataia.Document;
import com.scribd.presentation.AdapterWithAnalytics;

public class ArticleModuleHandler
        extends ModuleHandler<BasicDiscoverModuleWithMetadata, ArticleListItemViewHolder> {

    private static final String TAG = "ArticleModuleHandler";

    public ArticleModuleHandler(@NonNull Fragment fragment,
                                @NonNull ModuleDelegate moduleDelegate) {
        super(fragment, moduleDelegate);
    }

    @Override
    public void handleView(final BasicDiscoverModuleWithMetadata basicModule, final ArticleListItemViewHolder holder, int
            position, AdapterWithAnalytics parentAdapter) {
        final DiscoverModule discoverModule = basicModule.getDataObject();
        final Document article = discoverModule.getDocuments()[0];
        final ArticleListItemView articleListItem = holder.getArticleListItemView();

        articleListItem.setDocument(article);
        articleListItem.showSaveIcon(Analytics.LibraryPanel.Source.article_item);

        articleListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentLauncher.launch(article);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_article_list_item;
    }

    @Override
    public ArticleListItemViewHolder createViewHolder(View itemView) {
        return new ArticleListItemViewHolder(itemView);
    }

    @Override
    public boolean canHandle(@NonNull DiscoverModule discoverModule) {
        return DiscoverModule.Type.article.name().equals(discoverModule.getType());
    }

    @Override
    public boolean isDataValid(@NonNull DiscoverModule discoverModule) {
        if (discoverModule.getDocuments() == null || discoverModule.getDocuments().length != 1) {
            return false;
        }
        Document article = discoverModule.getDocuments()[0];
        return article != null && !TextUtils.isEmpty(discoverModule.getTitle());
    }

    @Override
    public BasicDiscoverModuleWithMetadata createDiscoverModuleWithMetadata(DiscoverModule discoverModule,
                                                                            DiscoverModuleWithMetadata.ModuleMetadata metadata) {
        return new BasicDiscoverModuleWithMetadataFactory(this, discoverModule, metadata).createSingleDocumentModule();
    }

    @Override
    public String toString() {
        return TAG;
    }
}
