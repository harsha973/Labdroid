package sha.com.ind.labapp.utils;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.logging.Logger;

import sha.com.ind.labapp.R;
import sha.com.ind.labapp.base.BaseActivity;

/**
 * Helper class used to show {@link Fragment}s on the screen.
 *
 */
public class FragmentManagerUtils {

	private static final Logger logger = Logger.getLogger(FragmentManagerUtils.class.getName());


	/**
	 * Adds the received {@link Fragment} in the container view with id "fragment_container".
	 *
	 * @param activity
	 * 		{@link Activity} that will contain the {@link Fragment}.
	 * @param fragment
	 * 		{@link Fragment} to be added.
	 * @param fragmentTag
	 * 		Tag of the {@link Fragment} to add, used to identify it.
	 */
	public static void addFragment(BaseActivity activity, Fragment fragment, String fragmentTag, @Animation int animation) {
		addFragment(activity, R.id.fragment_container, fragment, fragmentTag, animation);
	}

	/**
	 * Adds the received {@link Fragment} on the received container view (represented by its id).
	 *
	 * @param activity
	 * 		{@link Activity} that will contain the {@link Fragment}.
	 * @param containerViewId
	 * 		Id of the container view where the fragment will be added.
	 * @param fragment
	 * 		{@link Fragment} to be added.
	 * @param fragmentTag
	 * 		Tag of the {@link Fragment} to add, used to identify it.
	 */
	public static void addFragment(BaseActivity activity, int containerViewId, Fragment fragment, String fragmentTag, @Animation int animation) {
		showFragment(activity, containerViewId, fragment, fragmentTag, false, false, animation);
	}

	/**
	 * Adds the received {@link Fragment} in the container view with id "fragment_container", and adds
	 * it to the back stack as well.
	 *
	 * @param activity
	 * 		{@link Activity} that will contain the {@link Fragment}.
	 * @param fragment
	 * 		{@link Fragment} to be added.
	 * @param fragmentTag
	 * 		Tag of the {@link Fragment} to add, used to identify it.
	 */
	public static void addFragmentAndAddToBackStack(BaseActivity activity, Fragment fragment, String fragmentTag, @Animation int animation) {
		addFragmentAndAddToBackStack(activity, R.id.fragment_container, fragment, fragmentTag, animation);
	}

	/**
	 * Adds the received {@link Fragment} on the received container view (represented by its id), and adds
	 * it to the back stack as well.
	 *
	 * @param activity
	 * 		{@link Activity} that will contain the {@link Fragment}.
	 * @param containerViewId
	 * 		Id of the container view where the fragment will be added.
	 * @param fragment
	 * 		{@link Fragment} to be added.
	 * @param fragmentTag
	 * 		Tag of the {@link Fragment} to add, used to identify it.
	 */
	public static void addFragmentAndAddToBackStack(BaseActivity activity, int containerViewId, Fragment fragment, String fragmentTag, @Animation int animation) {
		showFragment(activity, containerViewId, fragment, fragmentTag, true, false, animation);
	}

	/**
	 * Replaces the current {@link Fragment} that is in the container view with id "fragment_container",
	 * with the new {@link Fragment} received.
	 *
	 * @param activity
	 * 		{@link Activity} that will contain the {@link Fragment}.
	 * @param fragment
	 * 		{@link Fragment} to be added, that will replace the current one.
	 * @param fragmentTag
	 * 		Tag of the {@link Fragment} to add, used to identify it.
	 */
	public static void replaceFragment(BaseActivity activity, Fragment fragment, String fragmentTag, @Animation int animation) {
		replaceFragment(activity, R.id.fragment_container, fragment, fragmentTag, animation);
	}

	/**
	 * Replaces the current {@link Fragment} that is in the container view with id "fragment_container",
	 * with the new {@link Fragment} received, and adds it to the back stack.
	 *
	 * @param activity
	 * 		{@link Activity} that will contain the {@link Fragment}.
	 * @param fragment
	 * 		{@link Fragment} to be added, that will replace the current one.
	 * @param fragmentTag
	 * 		Tag of the {@link Fragment} to add, used to identify it.
	 */
	public static void replaceFragmentAndAddToBackStack(BaseActivity activity, Fragment fragment, String fragmentTag, @Animation int animation) {
		showFragment(activity, R.id.fragment_container, fragment, fragmentTag, true, true, animation);
	}

	/**
	 * Replaces the current {@link Fragment} that is in the container view received (represented by its id),
	 * with the new {@link Fragment} received.
	 *
	 * @param activity
	 * 		{@link Activity} that will contain the {@link Fragment}.
	 * @param containerViewId
	 * 		Id of the container view where the new fragment will replace the current one.
	 * @param fragment
	 * 		{@link Fragment} to be added, that will replace the current one.
	 * @param fragmentTag
	 * 		Tag of the {@link Fragment} to add, used to identify it.
	 */
	public static void replaceFragment(BaseActivity activity, int containerViewId, Fragment fragment, String fragmentTag, @Animation int animation) {
		showFragment(activity, containerViewId, fragment, fragmentTag, false, true, animation);
	}

	/**
	 * Shows the received {@link Fragment} in the container view received (represented by its id),
	 * adding it to the back stack or not, and replacing the current one or not (depending on the
	 * received parameters "addToBackStack" and "replace").
	 *
	 * @param activity
	 * 		{@link Activity} that will contain the {@link Fragment}.
	 * @param containerViewId
	 * 		Id of the container view where the new fragment will replace the current one.
	 * @param fragment
	 * 		{@link Fragment} to be added, that will replace the current one.
	 * @param fragmentTag
	 * 		Tag of the {@link Fragment} to add, used to identify it.
	 * @param addToBackStack
	 * 		{@code true} if the {@link Fragment} should be added to the back stack, or {@code false} if it shouldn't.
	 * @param replace
	 * 		{@code true} if the current {@link Fragment} should be replaced, or {@code false} if it shouldn't.
	 */
	private static void showFragment(BaseActivity activity, int containerViewId, Fragment fragment, String fragmentTag, boolean addToBackStack, boolean replace, @Animation int animation) {
		FragmentManager fragmentManager = activity.getSupportFragmentManager();

		// Make sure the activity is not paused or destroyed before trying to show the fragment,
		// otherwise an IllegalStateException will happen
		if (/*activity.isPaused() ||*/ activity.isFinishing()) {
			logger.warning("Didn't show fragment \"" + fragment.getClass().getSimpleName() + "\" because activity \"" + activity.getClass().getSimpleName() + "\" is paused or destroyed.");

			return;
		}

		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		switch (animation) {
			case Animation.SLIDE_IN_RIGHT:
				fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
				break;
			case Animation.SLIDE_IN_BOTTOM:
				//	TODO
				fragmentTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.no_animation, R.anim.no_animation, R.anim.slide_out_bottom);
				break;
			case Animation.FADE_IN:
				fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
				break;
		}

		if (replace) {
			fragmentTransaction.replace(containerViewId, fragment, fragmentTag);
		} else {
			fragmentTransaction.add(containerViewId, fragment, fragmentTag);
		}

		if (addToBackStack) {
			fragmentTransaction.addToBackStack(fragmentTag);
		}

		fragmentTransaction.commit();
	}

	/*
	 * Inner classes, interfaces, enums
	 */
	@IntDef( {Animation.SLIDE_IN_RIGHT, Animation.FADE_IN, Animation.NONE, Animation.SLIDE_IN_BOTTOM} )
	@Retention(RetentionPolicy.SOURCE)
	public @interface Animation {

		int NONE = 0;
		int FADE_IN = 1;
		int SLIDE_IN_RIGHT = 2;
		int SLIDE_IN_BOTTOM = 3;
	}
}
