package net.invictaguild.content

class Forum {

    Date dateCreated
    Date lastUpdated

    String name
    String slug
    int sortPosition

    static belongsTo = [group:ForumGroup]
    static hasMany = [threads:Thread]

    static constraints = {
        name blank: false, unique: ['group']
        slug nullable: true, blank: false
    }

    def beforeInsert() {
        slug = name.slugify()
    }

    def beforeUpdate() {
        if (isDirty( 'name' ))
            slug = name.slugify()
    }
}
