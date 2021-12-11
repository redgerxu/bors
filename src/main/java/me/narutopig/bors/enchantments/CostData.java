package me.narutopig.bors.enchantments;

public class CostData {
    public final int[] indices;
    public final int lastOverflow;

    public CostData(int[] indices, int lastOverflow) {
        this.indices = indices;
        this.lastOverflow = lastOverflow;
    }
}
