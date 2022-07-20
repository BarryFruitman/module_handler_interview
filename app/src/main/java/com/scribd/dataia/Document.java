package com.scribd.dataia;

import com.scribd.presentationia.model.UserLegacy;

public class Document {
    public final Boolean isPodcastSeries = false;
    public final Boolean isIssue = false;
    public final Boolean isCanonical = false;
    public final Boolean isBook = false;
    public final Boolean isAudioBook = false;
    public final Boolean isPodcastEpisode = false;
    public final Boolean isUgc = false;
    public final Boolean isSheetMusic = false;
    public final Boolean isArticle = false;
    public final Boolean isCanonicalSummary = false;
    public final String title = "";
    public final String firstAuthorOrPublisherName = "";

    public EditorialBlurb getEditorialBlurb() {
        return new EditorialBlurb();
    }

    public int getServerId() {
        return 0;
    }

    public String getDocumentType() {
        return null;
    }

    public UserLegacy getPublisher() {
        return null;
    }

    public boolean hasRegularImage() {
        return false;
    }

    public boolean hasSquareImage() {
        return false;
    }

    public class EditorialBlurb {
        public CharSequence getHeader() {
            return null;
        }

        public CharSequence getDescription() {
            return null;
        }

        public CharSequence getTitle() {
            return null;
        }
    }
}
