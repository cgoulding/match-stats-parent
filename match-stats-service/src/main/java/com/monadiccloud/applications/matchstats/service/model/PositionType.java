package com.monadiccloud.applications.matchstats.service.model;

/**
 * @author Connor Goulding
 */
public enum PositionType
{
    GOAL_KEEPER(PositionAreaType.GOALS),
    LEFT_CORNER_BACK(PositionAreaType.BACK),
    FULL_BACK(PositionAreaType.BACK),
    RIGHT_CORNER_BACK(PositionAreaType.BACK),
    LEFT_WING_BACK(PositionAreaType.HALF_BACK),
    CENTRE_BACK(PositionAreaType.HALF_BACK),
    RIGHT_WING_BACK(PositionAreaType.HALF_BACK),
    MID_FIELD(PositionAreaType.MIDFIELD),
    LEFT_WING_FORWARD(PositionAreaType.HALF_FORWARD),
    CENTRE_FORWARD(PositionAreaType.HALF_FORWARD),
    RIGHT_WING_FORWARD(PositionAreaType.HALF_FORWARD),
    LEFT_CORNER_FORWARD(PositionAreaType.FORWARD),
    FULL_FORWARD(PositionAreaType.FORWARD),
    RIGHT_CORNER_FORWARD(PositionAreaType.FORWARD),
    UNDEFINED(PositionAreaType.UNDEFINED);

    public PositionAreaType area;

    PositionType(PositionAreaType area)
    {
        this.area = area;
    }
}
