package com.ghomerr.travelgates.objects;

import com.ghomerr.travelgates.constants.TravelGatesConstants;

public class TravelGatesPortalSign
{
	private String _destination = null;

	private boolean _validSign = false;

	private int _filledLine = -1;

	private int _emptyLine = -1;

	public TravelGatesPortalSign()
	{
		super();
	}

	public TravelGatesPortalSign(final String destination, final boolean validSign, final int filledLine,
			final int emptyLine)
	{
		super();
		this._destination = destination;
		this._validSign = validSign;
		this.setFilledLine(filledLine);
		this.setEmptyLine(emptyLine);
	}

	public String getDestination()
	{
		return _destination;
	}

	public void setDestination(final String destination)
	{
		this._destination = destination;
	}

	public boolean isValidSign()
	{
		return _validSign;
	}

	public void setValidSign(final boolean validSign)
	{
		this._validSign = validSign;
	}

	public void setFilledLine(final int filledLine)
	{
		this._filledLine = filledLine;
	}

	public int getFilledLine()
	{
		return _filledLine;
	}

	public void setEmptyLine(final int emptyLine)
	{
		this._emptyLine = emptyLine;
	}

	public int getEmptyLine()
	{
		return _emptyLine;
	}

	public TravelGatesPortalSign clone()
	{
		final String dest = (_destination == null) ? null : _destination.toString();

		return new TravelGatesPortalSign(dest, _validSign, _filledLine, _emptyLine);
	}

	@Override
	public String toString()
	{
		final StringBuilder strBuild = new StringBuilder();
		strBuild.append("PortalSign : {").append(_destination).append(TravelGatesConstants.DELIMITER)
				.append(_validSign).append(TravelGatesConstants.DELIMITER).append(_filledLine)
				.append(TravelGatesConstants.DELIMITER).append(_emptyLine).append("}");

		return strBuild.toString();
	}
}
