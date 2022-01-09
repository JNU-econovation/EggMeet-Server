package com.fivepotato.eggmeetserver.domain.Ban;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBan is a Querydsl query type for Ban
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBan extends EntityPathBase<Ban> {

    private static final long serialVersionUID = 1113529101L;

    public static final QBan ban = new QBan("ban");

    public final StringPath bannedEmail = createString("bannedEmail");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<Reason> reason = createEnum("reason", Reason.class);

    public QBan(String variable) {
        super(Ban.class, forVariable(variable));
    }

    public QBan(Path<? extends Ban> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBan(PathMetadata metadata) {
        super(Ban.class, metadata);
    }

}

