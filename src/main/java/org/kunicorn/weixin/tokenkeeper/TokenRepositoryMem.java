package org.kunicorn.weixin.tokenkeeper;

import org.springframework.stereotype.Component;

/**
 * 存在内存中，未作持久化
 */
@Component
public class TokenRepositoryMem implements TokenRepository {

    private Token last;

    private Token current;

    @Override
    public void save(Token token) {
        last = current;
        current = token;
    }

    @Override
    public Token findCurrent() {
        return current;
    }

    @Override
    public Token findLast() {
        return last;
    }
}
