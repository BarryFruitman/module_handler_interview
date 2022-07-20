package com.scribd.app.discover_modules.hero_issue;


import static com.scribd.app.discover_modules.DiscoverModule.MagazineAuxDataKey.MAGAZINE_IS_FOLLOWED;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.scribd.presentationia.model.UserLegacy;
import com.scribd.app.discover_modules.BasicDiscoverModuleWithMetadata;
import com.scribd.app.discover_modules.BasicDiscoverModuleWithMetadataFactory;
import com.scribd.app.discover_modules.DiscoverModule;
import com.scribd.app.discover_modules.DiscoverModuleWithMetadata;
import com.scribd.app.discover_modules.ModuleDelegate;
import com.scribd.app.discover_modules.ModuleHandler;
import com.scribd.app.discover_modules.ModuleViewHolder;
import com.scribd.dataia.Analytics;
import com.scribd.dataia.Document;
import com.scribd.presentation.AdapterWithAnalytics;
import com.scribd.presentation.thumbnail.ThumbnailView;
import com.scribd.presentation.view.SaveIcon;
import com.scribd.presentation.view.ScribdImageView;

public class HeroIssueHandler
        extends ModuleHandler<BasicDiscoverModuleWithMetadata, HeroIssueHandler.HeroIssueViewHolder> {

    private static final String TAG = "HeroIssueHandler";


    public HeroIssueHandler(Fragment fragment, ModuleDelegate moduleInterface) {
        super(fragment, moduleInterface);
    }

    @Override
    public void handleView(final BasicDiscoverModuleWithMetadata heroIssueModule,
                           HeroIssueViewHolder holder,
                           int position,
                           AdapterWithAnalytics parentAdapter) {

        DiscoverModule discoverModule = heroIssueModule.getDataObject();
        holder.publicationDate.setText(discoverModule.getTitle());

        Document document = discoverModule.getDocuments()[0];
        holder.thumbnail.setDocument(document);
        holder.saveIcon.setDocument(document, Analytics.LibraryPanel.Source.issue_hero);

        UserLegacy publisher = document.getPublisher();
        holder.publication.setText(publisher.getNameOrUsername());
        holder.publisherBlurb.setText(publisher.getEditorialBlurb().getDescription());
        setFollow(holder, discoverModule.getAuxDataAsBoolean(MAGAZINE_IS_FOLLOWED));

        holder.followContainer.setOnClickListener(view -> {
            boolean isFollowed = discoverModule.getAuxDataAsBoolean(MAGAZINE_IS_FOLLOWED);
            if (isFollowed) {
//                moduleDelegate.notifyItemAction(new HeroIssueWithArticleListFragment.UnfollowMagazineAction(
//                        new HeroIssueWithArticleListFragment.FollowMagazineActionData(position)
//                ));
            } else {
//                moduleDelegate.notifyItemAction(new HeroIssueWithArticleListFragment.FollowMagazineAction(
//                        new HeroIssueWithArticleListFragment.FollowMagazineActionData(position)
//                ));
            }
        });
    }

    private void setFollow(HeroIssueViewHolder holder, boolean isFollowed) {
        if (isFollowed) {
            holder.followIcon.setImageDrawable(
                    AppCompatResources.getDrawable(holder.itemView.getContext(), R.drawable.ic_followed_star_small));
            holder.followText.setText(R.string.unfollow);
        } else {
            holder.followIcon.setImageDrawable(
                    AppCompatResources.getDrawable(holder.followIcon.getContext(), R.drawable.ic_follow_star_small));
            holder.followText.setText(R.string.follow);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_hero_issue;
    }

    @Override
    public HeroIssueViewHolder createViewHolder(View itemView) {
        return new HeroIssueViewHolder(itemView);
    }

    @Override
    public boolean canHandle(@NonNull DiscoverModule discoverModule) {
        return DiscoverModule.Type.hero_issue.name().equals(discoverModule.getType());
    }

    @Override
    public boolean isDataValid(@NonNull DiscoverModule discoverModule) {
        return !TextUtils.isEmpty(discoverModule.getTitle())
                && hasValidDocument(discoverModule)
                && hasValidPublisher(discoverModule.getDocuments()[0]);

    }

    @Override
    public BasicDiscoverModuleWithMetadata createDiscoverModuleWithMetadata(DiscoverModule discoverModule,
                                                                            DiscoverModuleWithMetadata.ModuleMetadata metadata) {
        return new BasicDiscoverModuleWithMetadataFactory(this, discoverModule, metadata).createSingleDocumentModule();
    }

    private boolean hasValidDocument(DiscoverModule discoverModule) {
        return discoverModule.getDocuments() != null
                && discoverModule.getDocuments().length == 1
                && discoverModule.getDocuments()[0] != null;
    }

    private boolean hasValidPublisher(Document document) {
        UserLegacy publisher = document.getPublisher();
        return publisher != null
                && !TextUtils.isEmpty(publisher.getNameOrUsername())
                && publisher.getEditorialBlurb() != null
                && !TextUtils.isEmpty(publisher.getEditorialBlurb().getDescription());
    }

    @VisibleForTesting
    static class HeroIssueViewHolder extends ModuleViewHolder {
        public final ThumbnailView thumbnail;
        public final TextView publicationDate;
        public final TextView publication;
        public final SaveIcon saveIcon;
        public final LinearLayout followContainer;
        public final ScribdImageView followIcon;
        public final TextView followText;
        public final TextView publisherBlurb;

        public HeroIssueViewHolder(View itemView) {
            super(itemView);
            saveIcon = itemView.findViewById(R.id.saveIcon);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            publicationDate = itemView.findViewById(R.id.publicationDate);
            publication = itemView.findViewById(R.id.publicationTitle);
            publisherBlurb = itemView.findViewById(R.id.publisherBlurb);
            followIcon = itemView.findViewById(R.id.followIcon);
            followText = itemView.findViewById(R.id.followText);
            followContainer = itemView.findViewById(R.id.followContainer);
        }

    }

    @Override
    public String toString() {
        return TAG;
    }
}
