package org.swordofsouls.discord.chatexporter.Html.Builders.Message;

import jdk.jfr.ContentType;
import org.javacord.api.entity.Attachment;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedAuthor;
import org.javacord.api.entity.message.embed.EmbedField;
import org.javacord.api.entity.message.embed.EmbedFooter;
import org.swordofsouls.discord.chatexporter.Html.Builders.Components.ButtonBuilder;
import org.swordofsouls.discord.chatexporter.Html.Builders.HtmlBase;
import org.swordofsouls.discord.chatexporter.Html.Html;
import org.swordofsouls.discord.chatexporter.Html.HtmlFile;
import org.swordofsouls.discord.chatexporter.Utils.Color.ButtonStyleUtils;
import org.swordofsouls.discord.chatexporter.Utils.File.FileUtils;
import org.swordofsouls.discord.chatexporter.Utils.Time.TimeUtils;

import java.awt.datatransfer.MimeTypeParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class MessageCore extends HtmlBase {
    public MessageCore(HtmlFile file) {
        super(file);
        file.replace("EMOJI","");
    }

    @Override
    public HtmlFile build() {
        file.replace("EDIT","");
        return getFile();
    }

    public void setMessageId(Long messageId) {
        file.replace("MESSAGE_ID", messageId.toString());
    }
    public void setContent(String content) {
        HtmlFile messageContentLineF = Html.Message.CONTENT();
        messageContentLineF.replace("MESSAGE_CONTENT", content);
        file.replace("MESSAGE_CONTENT", messageContentLineF.getContent());
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
    public void setComponents(List<HighLevelComponent> components) {
        StringBuilder componentStringBuilder = new StringBuilder();
        for(HighLevelComponent component : components) {
            if(component.asActionRow().isPresent()) {
                ActionRow row = component.asActionRow().get();
                for(LowLevelComponent lowLevelComponent : row.getComponents()) {
                    if(lowLevelComponent.isButton() && lowLevelComponent.asButton().isPresent()) {
                        Button button = lowLevelComponent.asButton().get();
                        ButtonBuilder buttonBuilder = new ButtonBuilder(Html.Component.BUTTON());
                        boolean disabled;
                        if(button.isDisabled().isPresent()) disabled = button.isDisabled().get();
                        else disabled = false;
                        buttonBuilder.setColor(ButtonStyleUtils.buttonStyle(button.getStyle(), disabled));
                        buttonBuilder.setDisabled(disabled);
                        if(button.getLabel().isPresent()) buttonBuilder.setLabel(button.getLabel().get());
                        if(button.getUrl().isPresent()) buttonBuilder.setUrl(button.getUrl().get());
                        if(button.getEmoji().isPresent()) {
                            if(button.getEmoji().get().asUnicodeEmoji().isPresent()) buttonBuilder.setEmoji(button.getEmoji().get().asUnicodeEmoji().get());
                        }
                        componentStringBuilder.append(buttonBuilder.build().getContent());
                    }
                }
            }
        }
        file.replace("COMPONENTS",componentStringBuilder.toString());
    }
    public void setEditedTimestamp(Instant instant, ZoneId zone) {
        HtmlFile edit = Html.Message.EDITED();
        edit.replace("TIMESTAMP", TimeUtils.getFullFormattedTime(instant, zone));
        file.replace("EDIT", edit.getContent());
    }
    public void setAttachments(List<MessageAttachment> attachments) {
        StringBuilder attachmentBuilder = new StringBuilder();
        for(MessageAttachment attachment : attachments) {
            String extension = attachment.getFileName().split("\\.",2)[1];
            String icon = FileUtils.getIcon(attachment.getFileName());
            if(FileUtils.IMAGE_TYPES.contains(extension)) {
                HtmlFile image = Html.Attachment.IMAGE();
                image.replace("ATTACH_URL", attachment.getUrl().toString());
                image.replace("ATTACH_URL_THUMB", attachment.getUrl().toString());
                attachmentBuilder.append(image.getContent());
                continue;
            }
            if(FileUtils.DOCUMENT_TYPES.contains(extension) || FileUtils.ACROBAT_TYPES.contains(extension)
            || FileUtils.CODE_TYPES.contains(extension) || FileUtils.WEBCODE_TYPES.contains(extension)) {
                HtmlFile image = Html.Attachment.MESSAGE();
                image.replace("ATTACH_URL", attachment.getUrl().toString());
                image.replace("ATTACH_BYTES",
                        org.apache.commons.io.FileUtils.byteCountToDisplaySize(attachment.getSize()));
                image.replace("ATTACH_FILE", attachment.getFileName());
                image.replace("ATTACH_ICON", icon);
                attachmentBuilder.append(image.getContent());
                continue;
            }
            if(FileUtils.VIDEO_TYPES.contains(extension)) {
                HtmlFile image = Html.Attachment.VIDEO();
                image.replace("ATTACH_URL", attachment.getUrl().toString());
                attachmentBuilder.append(image.getContent());
                continue;
            }
            if(FileUtils.AUDIO_TYPES.contains(extension)) {
                HtmlFile image = Html.Attachment.AUDIO();
                image.replace("ATTACH_ICON", icon);
                image.replace("ATTACH_URL", attachment.getUrl().toString());
                image.replace("ATTACH_BYTES",
                        org.apache.commons.io.FileUtils.byteCountToDisplaySize(attachment.getSize()));
                image.replace("ATTACH_AUDIO", attachment.getUrl().toString());
                image.replace("ATTACH_FILE", attachment.getFileName());
                attachmentBuilder.append(image.getContent());
            }
        }
        file.replace("ATTACHMENTS", attachmentBuilder.toString());
    }
}
