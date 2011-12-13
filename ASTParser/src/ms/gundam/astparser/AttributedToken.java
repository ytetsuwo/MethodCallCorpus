package ms.gundam.astparser;

import ms.gundam.astparser.token.Token;

public class AttributedToken {

	private final Token token;

	private final ATTRIBUTE attribute;

	private int index;

	public AttributedToken(final Token token, final ATTRIBUTE attribute,
			final int index) {
		this.token = token;
		this.attribute = attribute;
		this.index = index;
	}

	public Token getToken() {
		return this.token;
	}

	public ATTRIBUTE getAttribute() {
		return this.attribute;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(final int index) {
		this.index = index;
	}
}
