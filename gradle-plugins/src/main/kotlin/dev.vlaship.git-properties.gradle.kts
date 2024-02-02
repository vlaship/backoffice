plugins {
    id("com.gorylenko.gradle-git-properties")
}

gitProperties {
    dateFormat = "yyyy-MM-dd HH:mm:ssZ"
    dateFormatTimeZone = "GMT"
    keys = arrayListOf("git.branch", "git.commit.id", "git.commit.id.abbrev", "git.commit.time", "git.tags")
}
