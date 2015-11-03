package com.bafomdad.zenscape.util;

import net.minecraft.util.MathHelper;

public class Rainbow {
	
	public static float r(float phase) {
		
		phase = (float) (phase * 6.283185307179586D);
		float r = (MathHelper.sin(phase + 0.0F) + 1.0F) * 0.5F;
		float g = (MathHelper.sin(phase + 2.094395F) + 1.0F) * 0.5F;
		float b = (MathHelper.sin(phase + 4.18879F) + 1.0F) * 0.5F;
		float resat = Math.min(r, Math.min(g, b));
		r -= resat;
		g -= resat;
		b -= resat;
		float scaler = 1.0F / Math.max(r, Math.max(g, b));

		r = Math.min(scaler * r, 1.0F);
		g = Math.min(scaler * g, 1.0F);
		b = Math.min(scaler * b, 1.0F);

		return r;
	}

	public static float g(float phase) {
		
		phase = (float) (phase * 6.283185307179586D);
		float r = (MathHelper.sin(phase + 0.0F) + 1.0F) * 0.5F;
		float g = (MathHelper.sin(phase + 2.094395F) + 1.0F) * 0.5F;
		float b = (MathHelper.sin(phase + 4.18879F) + 1.0F) * 0.5F;
		float resat = Math.min(r, Math.min(g, b));
		r -= resat;
		g -= resat;
		b -= resat;
		float scaler = 1.0F / Math.max(r, Math.max(g, b));

		r = Math.min(scaler * r * 0.5F + 0.5F, 1.0F);
		g = Math.min(scaler * g * 0.5F + 0.5F, 1.0F);
		b = Math.min(scaler * b * 0.5F + 0.5F, 1.0F);

		return g;
	}

	public static float b(float phase) {
		
		phase = (float) (phase * 6.283185307179586D);
		float r = (MathHelper.sin(phase + 0.0F) + 1.0F) * 0.5F;
		float g = (MathHelper.sin(phase + 2.094395F) + 1.0F) * 0.5F;
		float b = (MathHelper.sin(phase + 4.18879F) + 1.0F) * 0.5F;
		float resat = Math.min(r, Math.min(g, b));
		r -= resat;
		g -= resat;
		b -= resat;
		float scaler = 1.0F / Math.max(r, Math.max(g, b));

		r = Math.min(scaler * r * 0.5F + 0.5F, 1.0F);
		g = Math.min(scaler * g * 0.5F + 0.5F, 1.0F);
		b = Math.min(scaler * b * 0.5F + 0.5F, 1.0F);

		return b;
	}
}