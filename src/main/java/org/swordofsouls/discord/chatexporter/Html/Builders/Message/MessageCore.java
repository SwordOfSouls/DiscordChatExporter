package org.swordofsouls.discord.chatexporter.Html.Builders.Message;

import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedAuthor;
import org.javacord.api.entity.message.embed.EmbedField;
import org.javacord.api.entity.message.embed.EmbedFooter;
import org.swordofsouls.discord.chatexporter.Html.Builders.HtmlBase;
import org.swordofsouls.discord.chatexporter.Html.Html;
import org.swordofsouls.discord.chatexporter.Html.HtmlFile;

import java.util.List;

public class MessageCore extends HtmlBase {
    public MessageCore(HtmlFile file) {
        super(file);
        file.replace("ATTACHMENTS","");
        file.replace("COMPONENTS","");
        file.replace("EMOJI","");
    }

    @Override
    public HtmlFile build() {
        return getFile();
    }

    public void setMessageId(Long messageId) {
        file.replace("MESSAGE_ID", messageId.toString());
    }
    public void setContent(String content) {
        file.replace("MESSAGE_CONTENT", content);
    }
    public void setEmbeds(List<Embed> embeds) {
        StringBuilder embedStringBuilder = new StringBuilder();
        for(Embed embed : embeds) {
            HtmlFile embedF = Html.Embed.BODY();
            embedF.replace("EMBED_R", String.valueOf(embed.getColor().get().getRed()));
            embedF.replace("EMBED_G", String.valueOf(embed.getColor().get().getGreen()));
            embedF.replace("EMBED_B", String.valueOf(embed.getColor().get().getBlue()));
            if (embed.getAuthor().isPresent()) {
                EmbedAuthor author = embed.getAuthor().get();
                HtmlFile embedAuthorF = Html.Embed.AUTHOR();
                embedAuthorF.replace("AUTHOR", author.getName());
                embedF.replace("EMBED_AUTHOR", embedAuthorF.getContent());
            }
            else embedF.replace("EMBED_AUTHOR", "");
            if (embed.getTitle().isPresent()) embedF.replace("EMBED_TITLE", Html.Embed.TITLE().replace("EMBED_TITLE",
                    embed.getTitle().get()).getContent());
            else embedF.replace("EMBED_TITLE", "");

            if (embed.getDescription().isPresent()) {
                HtmlFile embedDescriptionF = Html.Embed.DESCRIPTION();
                embedDescriptionF.replace("EMBED_DESC", embed.getDescription().get());
                embedF.replace("EMBED_DESC", embedDescriptionF.getContent());
            } else embedF.replace("EMBED_DESC", "");

            StringBuilder embedsBuilder = new StringBuilder();
            for (EmbedField embedField : embed.getFields()) {
                HtmlFile embedFieldF;
                if (embedField.isInline()) embedFieldF = Html.Embed.FIELD_INLINE();
                else embedFieldF = Html.Embed.FIELD();

                embedFieldF.replace("FIELD_NAME", embedField.getName());
                embedFieldF.replace("FIELD_VALUE", embedField.getValue());
                embedsBuilder.append(embedFieldF.getContent());
            }
            embedF.replace("EMBED_FIELDS", embedsBuilder.toString());

            if (embed.getImage().isPresent()) embedF.replace("EMBED_IMAGE", embed.getImage().get().getUrl().toString());
            else embedF.replace("EMBED_IMAGE", "");

            if (embed.getThumbnail().isPresent()) embedF.replace("EMBED_THUMBNAIL",
                    embed.getThumbnail().get().getUrl().toString());
            else embedF.replace("EMBED_THUMBNAIL", "");

            if (embed.getFooter().isPresent()) {
                EmbedFooter footer = embed.getFooter().get();
                HtmlFile footerF;

                if (footer.getIconUrl().isPresent()) {
                    footerF = Html.Embed.FOOTER_IMAGE();
                    footerF.replace("EMBED_FOOTER_ICON", footer.getIconUrl().get().toString());
                } else {
                    footerF = Html.Embed.FOOTER();
                }
                if (footer.getText().isPresent()) footerF.replace("EMBED_FOOTER", footer.getText().get());
                else footerF.replace("EMBED_FOOTER", "");

                embedF.replace("EMBED_FOOTER", footerF.getContent());
            } else {
                embedF.replace("EMBED_FOOTER", "");
            }
            embedStringBuilder.append(embedF.getContent());
        }
        file.replace("EMBEDS", embedStringBuilder.toString());
    }
}
