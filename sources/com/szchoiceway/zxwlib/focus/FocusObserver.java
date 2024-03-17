package com.szchoiceway.zxwlib.focus;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.recyclerview.widget.RecyclerView;
import com.szchoiceway.zxwlib.focus.view.BTCircleDialView;
import com.szchoiceway.zxwlib.focus.view.BTDailView;
import com.szchoiceway.zxwlib.focus.view.FocusAdapter;
import com.szchoiceway.zxwlib.focus.view.FocusButton;
import com.szchoiceway.zxwlib.focus.view.FocusCheckBox;
import com.szchoiceway.zxwlib.focus.view.FocusIconWithTitleView;
import com.szchoiceway.zxwlib.focus.view.FocusImageButton;
import com.szchoiceway.zxwlib.focus.view.FocusImageView;
import com.szchoiceway.zxwlib.focus.view.FocusListView;
import com.szchoiceway.zxwlib.focus.view.FocusRadioButton;
import com.szchoiceway.zxwlib.focus.view.FocusRecycleView;
import com.szchoiceway.zxwlib.focus.view.FocusRecyclerViewAdapter;
import com.szchoiceway.zxwlib.focus.view.FocusTextView;
import com.szchoiceway.zxwlib.utils.LogUtils;
import java.util.HashMap;
import java.util.Map;

public class FocusObserver {
    public static final int DISPLAY_SINGAL_PAGE = 0;
    public static final int DISPLAY_TWO_PAGE = 1;
    public static final int JUMP_TO_OTHER_PAGE = 666;
    public static final String TAG = "FocusObserver";
    Map<Integer, ViewInfo> mAllFocusViewMap = new HashMap();
    boolean mControlByKnob = false;
    String mCurFocusPage = "page1";
    int mCurFocusViewId;
    View mCurParentView;
    View mCurParentView2;
    int mCurPosition = -1;
    int mDisplayMode = 0;
    Map<Integer, ViewInfo> mFocusViewMap = new HashMap();
    Map<Integer, ViewInfo> mFocusViewMap2 = new HashMap();
    int mOldFocusViewId;
    private OnFocusViewChangeListener mOnFocusViewChangeListener = null;
    View oldFocusedView;

    public interface OnFocusViewChangeListener {
        void onChange(int i, int i2);
    }

    public void setLayout(View view) {
        this.mCurParentView = view;
        this.mFocusViewMap.clear();
        getAllChildViews(view, this.mFocusViewMap, "page1");
    }

    public void setLayout2(View view) {
        Log.i(TAG, "setLayout2: domove--------------------");
        this.mCurParentView2 = view;
        this.mFocusViewMap2.clear();
        getAllChildViews(view, this.mFocusViewMap2, "page2");
    }

    private void getAllChildViews(View view, Map<Integer, ViewInfo> map, String str) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getVisibility() == 0) {
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View childAt = viewGroup.getChildAt(i);
                    Object tag = childAt.getTag();
                    if (tag == null || !"ignoreFocus".equals(tag.toString())) {
                        if (isNeedListChild(childAt)) {
                            getAllChildViews(childAt, map, str);
                        } else {
                            Object tag2 = childAt.getTag();
                            if (tag2 != null) {
                                String obj = tag2.toString();
                                LogUtils.i(TAG, "getAllChildViews:       " + str);
                                if (!TextUtils.isEmpty(obj) && obj.contains("focus") && isNeedAdd(childAt)) {
                                    ViewInfo viewInfo = new ViewInfo();
                                    int id = childAt.getId();
                                    viewInfo.setViewId(id);
                                    viewInfo.setNextFocusLeftId(childAt.getNextFocusLeftId());
                                    viewInfo.setNextFocusRightId(childAt.getNextFocusRightId());
                                    viewInfo.setNextFocusUpId(childAt.getNextFocusUpId());
                                    viewInfo.setNextFocusDownId(childAt.getNextFocusDownId());
                                    String simpleName = childAt.getClass().getSimpleName();
                                    Log.i(TAG, "getAllChildViews: " + simpleName);
                                    viewInfo.setViewClassName(simpleName);
                                    viewInfo.setPage(str);
                                    map.put(Integer.valueOf(id), viewInfo);
                                    this.mAllFocusViewMap.put(Integer.valueOf(id), viewInfo);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void doNextFocus(int i) {
        LogUtils.i(TAG, "doNextFocus:key==     " + i);
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            doMove(i);
        } else if (i == 5) {
            doEnter();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void doEnter() {
        /*
            r5 = this;
            java.util.Map<java.lang.Integer, com.szchoiceway.zxwlib.focus.ViewInfo> r0 = r5.mAllFocusViewMap
            int r1 = r5.mCurFocusViewId
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.lang.Object r0 = r0.get(r1)
            com.szchoiceway.zxwlib.focus.ViewInfo r0 = (com.szchoiceway.zxwlib.focus.ViewInfo) r0
            if (r0 == 0) goto L_0x0084
            java.lang.String r0 = r0.getPage()
            java.lang.String r1 = "page2"
            boolean r0 = r1.equals(r0)
            r1 = 0
            if (r0 == 0) goto L_0x0028
            android.view.View r0 = r5.mCurParentView2
            if (r0 == 0) goto L_0x0033
            int r2 = r5.mCurFocusViewId
            android.view.View r0 = r0.findViewById(r2)
            goto L_0x0034
        L_0x0028:
            android.view.View r0 = r5.mCurParentView
            if (r0 == 0) goto L_0x0033
            int r2 = r5.mCurFocusViewId
            android.view.View r0 = r0.findViewById(r2)
            goto L_0x0034
        L_0x0033:
            r0 = r1
        L_0x0034:
            if (r0 == 0) goto L_0x0084
            boolean r2 = r0 instanceof com.szchoiceway.zxwlib.focus.view.FocusListView
            if (r2 == 0) goto L_0x0052
            com.szchoiceway.zxwlib.focus.view.FocusListView r0 = (com.szchoiceway.zxwlib.focus.view.FocusListView) r0
            android.widget.ListAdapter r2 = r0.getAdapter()
            com.szchoiceway.zxwlib.focus.view.FocusAdapter r2 = (com.szchoiceway.zxwlib.focus.view.FocusAdapter) r2
            int r2 = r2.getCount()
            if (r2 <= 0) goto L_0x0084
            int r2 = r5.mCurPosition
            if (r2 < 0) goto L_0x0084
            r3 = 0
            r0.performItemClick(r1, r2, r3)
            goto L_0x0084
        L_0x0052:
            boolean r1 = r0 instanceof com.szchoiceway.zxwlib.focus.view.FocusRecycleView
            if (r1 == 0) goto L_0x006c
            com.szchoiceway.zxwlib.focus.view.FocusRecycleView r0 = (com.szchoiceway.zxwlib.focus.view.FocusRecycleView) r0
            androidx.recyclerview.widget.RecyclerView$Adapter r0 = r0.getAdapter()
            com.szchoiceway.zxwlib.focus.view.FocusRecyclerViewAdapter r0 = (com.szchoiceway.zxwlib.focus.view.FocusRecyclerViewAdapter) r0
            int r1 = r0.getItemCount()
            if (r1 <= 0) goto L_0x0084
            int r1 = r5.mCurPosition
            if (r1 < 0) goto L_0x0084
            r0.performItemClick(r1)
            goto L_0x0084
        L_0x006c:
            boolean r1 = r0 instanceof com.szchoiceway.zxwlib.focus.view.BTDailView
            if (r1 == 0) goto L_0x0077
            com.szchoiceway.zxwlib.focus.view.BTDailView r0 = (com.szchoiceway.zxwlib.focus.view.BTDailView) r0
            r1 = 0
            r0.enterDailPane(r1)
            goto L_0x0084
        L_0x0077:
            boolean r1 = r0 instanceof com.szchoiceway.zxwlib.focus.view.BTCircleDialView
            if (r1 == 0) goto L_0x0081
            com.szchoiceway.zxwlib.focus.view.BTCircleDialView r0 = (com.szchoiceway.zxwlib.focus.view.BTCircleDialView) r0
            r0.doClick()
            goto L_0x0084
        L_0x0081:
            r0.performClick()
        L_0x0084:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.zxwlib.focus.FocusObserver.doEnter():void");
    }

    private void doMove(int i) {
        View view;
        ViewInfo nextFocusViewId = getNextFocusViewId(i);
        if (nextFocusViewId != null) {
            int viewId = nextFocusViewId.getViewId();
            String page = nextFocusViewId.getPage();
            this.mCurFocusPage = page;
            LogUtils.i(TAG, "获取到的下一view的信息==   " + nextFocusViewId.toString());
            View view2 = null;
            View view3 = this.mCurParentView;
            if (view3 != null) {
                view2 = view3.findViewById(viewId);
            }
            if ("page2".equals(page) && (view = this.mCurParentView2) != null) {
                view2 = view.findViewById(viewId);
            }
            boolean z = false;
            Log.i(TAG, "doMove:---------- " + (this.mCurParentView2 == null) + "  |  " + (view2 == null));
            clearOldFocus(view2);
            if (view2 != null) {
                if (view2 instanceof FocusListView) {
                    FocusListView focusListView = (FocusListView) view2;
                    FocusAdapter focusAdapter = (FocusAdapter) focusListView.getAdapter();
                    int count = focusAdapter.getCount();
                    if (count > 0) {
                        int firstVisiblePosition = focusListView.getFirstVisiblePosition();
                        Log.i(TAG, "获取到的下一listview的item位置: " + firstVisiblePosition + " |  " + this.mCurPosition + " |  " + (this.mCurFocusViewId != viewId));
                        if (this.mCurPosition < 0 || this.mCurFocusViewId != viewId) {
                            this.mCurPosition = firstVisiblePosition;
                        }
                        if (this.mCurPosition >= count) {
                            this.mCurPosition = count - 1;
                        }
                        LogUtils.i(TAG, "获取到的下一listview的item位置==   " + this.mCurPosition);
                        focusAdapter.setCurFocusItemPosition(this.mCurPosition);
                        this.mCurFocusViewId = viewId;
                        focusAdapter.notifyDataSetChanged();
                        focusListView.setSelection(this.mCurPosition);
                    }
                } else if (view2 instanceof BTDailView) {
                    if (this.mCurPosition < 0) {
                        this.mCurPosition = 13;
                    }
                    if (this.mCurPosition >= 14) {
                        this.mCurPosition = 0;
                    }
                    ((BTDailView) view2).setFocusIndex(this.mCurPosition);
                } else if (view2 instanceof BTCircleDialView) {
                    BTCircleDialView bTCircleDialView = (BTCircleDialView) view2;
                    int maxLength = bTCircleDialView.getMaxLength();
                    if (this.mCurPosition < 0) {
                        this.mCurPosition = maxLength - 1;
                    }
                    if (this.mCurPosition >= maxLength) {
                        this.mCurPosition = 0;
                    }
                    bTCircleDialView.setFocusIndex(this.mCurPosition);
                } else if (view2 instanceof FocusRecycleView) {
                    FocusRecycleView focusRecycleView = (FocusRecycleView) view2;
                    FocusRecyclerViewAdapter focusRecyclerViewAdapter = (FocusRecyclerViewAdapter) focusRecycleView.getAdapter();
                    int itemCount = focusRecyclerViewAdapter.getItemCount();
                    if (itemCount > 0) {
                        int firstVisiblePosition2 = focusRecycleView.getFirstVisiblePosition();
                        Log.i(TAG, "获取到的下一RecycleView的item位置: " + firstVisiblePosition2 + " |  " + this.mCurPosition + " |  " + (this.mCurFocusViewId != viewId));
                        if (this.mCurPosition < 0 || this.mCurFocusViewId != viewId) {
                            this.mCurPosition = firstVisiblePosition2;
                        }
                        if (this.mCurPosition >= itemCount) {
                            this.mCurPosition = itemCount - 1;
                        }
                        LogUtils.i(TAG, "获取到的下一RecycleView的item位置==   " + this.mCurPosition);
                        focusRecyclerViewAdapter.setCurFocusItemPosition(this.mCurPosition);
                        this.mCurFocusViewId = viewId;
                        focusRecyclerViewAdapter.notifyDataSetChanged();
                        focusRecycleView.scrollToPosition(this.mCurPosition);
                    }
                }
                StringBuilder append = new StringBuilder().append("doMove:----xx------ ");
                if (view2 == null) {
                    z = true;
                }
                Log.i(TAG, append.append(z).toString());
                drawFocusDirection(view2);
            }
            View view4 = this.oldFocusedView;
            if (view4 != null) {
                this.mOldFocusViewId = view4.getId();
            }
            this.mCurFocusViewId = viewId;
            this.oldFocusedView = view2;
            sendFocusChangeInfo();
            doEnterWhenFocused(this.oldFocusedView);
        }
    }

    private void doEnterWhenFocused(View view) {
        if (view == null) {
            return;
        }
        if (view instanceof FocusTextView) {
            if (((FocusTextView) view).ismDoEnterWhenFocused()) {
                doEnter();
            }
        } else if (view instanceof FocusButton) {
            if (((FocusButton) view).ismDoEnterWhenFocused()) {
                doEnter();
            }
        } else if (view instanceof FocusImageView) {
            if (((FocusImageView) view).ismDoEnterWhenFocused()) {
                doEnter();
            }
        } else if (view instanceof FocusRadioButton) {
            if (((FocusRadioButton) view).ismDoEnterWhenFocused()) {
                doEnter();
            }
        } else if (view instanceof FocusImageButton) {
            if (((FocusImageButton) view).ismDoEnterWhenFocused()) {
                doEnter();
            }
        } else if (view instanceof FocusCheckBox) {
            if (((FocusCheckBox) view).ismDoEnterWhenFocused()) {
                doEnter();
            }
        } else if ((view instanceof FocusIconWithTitleView) && ((FocusIconWithTitleView) view).ismDoEnterWhenFocused()) {
            doEnter();
        }
    }

    private void drawFocusDirection(View view) {
        if (view != null) {
            LogUtils.i(TAG, "drawFocusDirection:当前显示焦点的View     " + Integer.toHexString(view.getId()));
            if (view instanceof FocusListView) {
                ((FocusListView) view).setFocusedState();
            } else if (view instanceof BTDailView) {
                ((BTDailView) view).setFocusedState();
            } else if (view instanceof BTCircleDialView) {
                ((BTCircleDialView) view).setFocusedState();
            } else if (view instanceof FocusTextView) {
                ((FocusTextView) view).setFocusedState();
            } else if (view instanceof FocusButton) {
                ((FocusButton) view).setFocusedState();
            } else if (view instanceof FocusImageView) {
                ((FocusImageView) view).setFocusedState();
            } else if (view instanceof FocusRadioButton) {
                ((FocusRadioButton) view).setFocusedState();
            } else if (view instanceof FocusImageButton) {
                ((FocusImageButton) view).setFocusedState();
            } else if (view instanceof FocusCheckBox) {
                ((FocusCheckBox) view).setFocusedState();
            } else if (view instanceof FocusIconWithTitleView) {
                ((FocusIconWithTitleView) view).setFocusedState();
            } else if (view instanceof FocusRecycleView) {
                ((FocusRecycleView) view).setFocusedState();
            }
        }
    }

    public void clearOldFocus(View view) {
        if (this.oldFocusedView != null) {
            LogUtils.i(TAG, "clearOldFocus:当前清除焦点的View     " + Integer.toHexString(this.oldFocusedView.getId()));
            View view2 = this.oldFocusedView;
            if (view2 instanceof FocusListView) {
                if (view2 != view) {
                    FocusAdapter focusAdapter = (FocusAdapter) ((FocusListView) view2).getAdapter();
                    this.mCurPosition = -1;
                    focusAdapter.setCurFocusItemPosition(-1);
                    focusAdapter.notifyDataSetChanged();
                }
                ((FocusListView) this.oldFocusedView).clearFocusedState();
            } else if (view2 instanceof BTDailView) {
                ((BTDailView) view2).clearFocusedState();
            } else if (view2 instanceof BTCircleDialView) {
                ((BTCircleDialView) view2).clearFocusedState();
            } else if (view2 instanceof FocusTextView) {
                ((FocusTextView) view2).clearFocusedState();
            } else if (view2 instanceof FocusButton) {
                ((FocusButton) view2).clearFocusedState();
            } else if (view2 instanceof FocusImageView) {
                ((FocusImageView) view2).clearFocusedState();
            } else if (view2 instanceof FocusRadioButton) {
                ((FocusRadioButton) view2).clearFocusedState();
            } else if (view2 instanceof FocusImageButton) {
                ((FocusImageButton) view2).clearFocusedState();
            } else if (view2 instanceof FocusCheckBox) {
                ((FocusCheckBox) view2).clearFocusedState();
            } else if (view2 instanceof FocusIconWithTitleView) {
                ((FocusIconWithTitleView) view2).clearFocusedState();
            } else if (view2 instanceof FocusRecycleView) {
                if (view2 != view) {
                    FocusRecyclerViewAdapter focusRecyclerViewAdapter = (FocusRecyclerViewAdapter) ((FocusRecycleView) view2).getAdapter();
                    this.mCurPosition = -1;
                    focusRecyclerViewAdapter.setCurFocusItemPosition(-1);
                    focusRecyclerViewAdapter.notifyDataSetChanged();
                }
                ((FocusRecycleView) this.oldFocusedView).clearFocusedState();
            }
        }
    }

    public void clearAppointFocus(View view) {
        if (view != null) {
            LogUtils.i(TAG, "clearAppointFocus:当前清除焦点的View     " + Integer.toHexString(view.getId()));
            if (view instanceof FocusListView) {
                ((FocusListView) view).clearFocusedState();
            } else if (view instanceof BTDailView) {
                ((BTDailView) view).clearFocusedState();
            } else if (view instanceof BTCircleDialView) {
                ((BTCircleDialView) view).clearFocusedState();
            } else if (view instanceof FocusTextView) {
                ((FocusTextView) view).clearFocusedState();
            } else if (view instanceof FocusButton) {
                ((FocusButton) view).clearFocusedState();
            } else if (view instanceof FocusImageView) {
                ((FocusImageView) view).clearFocusedState();
            } else if (view instanceof FocusRadioButton) {
                ((FocusRadioButton) view).clearFocusedState();
            } else if (view instanceof FocusImageButton) {
                ((FocusImageButton) view).clearFocusedState();
            } else if (view instanceof FocusCheckBox) {
                ((FocusCheckBox) view).clearFocusedState();
            } else if (view instanceof FocusIconWithTitleView) {
                ((FocusIconWithTitleView) view).clearFocusedState();
            } else if (view instanceof FocusRecycleView) {
                FocusRecyclerViewAdapter focusRecyclerViewAdapter = (FocusRecyclerViewAdapter) ((FocusRecycleView) this.oldFocusedView).getAdapter();
                this.mCurPosition = -1;
                focusRecyclerViewAdapter.setCurFocusItemPosition(-1);
                focusRecyclerViewAdapter.notifyDataSetChanged();
                ((FocusRecycleView) this.oldFocusedView).clearFocusedState();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00d0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.szchoiceway.zxwlib.focus.ViewInfo getNextFocusViewId(int r14) {
        /*
            r13 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "getNextFocusViewId:当前焦点位置View的id== 0x"
            java.lang.StringBuilder r0 = r0.append(r1)
            int r1 = r13.mCurFocusViewId
            java.lang.String r1 = java.lang.Integer.toHexString(r1)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "FocusObserver"
            com.szchoiceway.zxwlib.utils.LogUtils.i(r1, r0)
            java.util.Map<java.lang.Integer, com.szchoiceway.zxwlib.focus.ViewInfo> r0 = r13.mAllFocusViewMap
            int r2 = r13.mCurFocusViewId
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            java.lang.Object r0 = r0.get(r2)
            com.szchoiceway.zxwlib.focus.ViewInfo r0 = (com.szchoiceway.zxwlib.focus.ViewInfo) r0
            r2 = 0
            if (r0 == 0) goto L_0x01b9
            java.lang.String r3 = r0.getViewClassName()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "getNextFocusViewId:当前焦点位置View的类名== "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r3)
            java.lang.String r4 = r4.toString()
            com.szchoiceway.zxwlib.utils.LogUtils.i(r1, r4)
            java.lang.String r4 = "FocusListView"
            boolean r4 = r3.equals(r4)
            r5 = 4
            r6 = 3
            r7 = 2
            r8 = 1
            if (r4 != 0) goto L_0x008c
            java.lang.String r4 = "BTDailView"
            boolean r4 = r3.equals(r4)
            if (r4 != 0) goto L_0x008c
            java.lang.String r4 = "FocusRecycleView"
            boolean r4 = r3.endsWith(r4)
            if (r4 != 0) goto L_0x008c
            java.lang.String r4 = "BTCircleDialView"
            boolean r3 = r3.endsWith(r4)
            if (r3 == 0) goto L_0x006e
            goto L_0x008c
        L_0x006e:
            if (r14 == r8) goto L_0x0087
            if (r14 == r7) goto L_0x0082
            if (r14 == r6) goto L_0x007d
            if (r14 == r5) goto L_0x0078
        L_0x0076:
            r14 = r2
            goto L_0x00b2
        L_0x0078:
            int r14 = r0.getNextFocusDownId()
            goto L_0x00b2
        L_0x007d:
            int r14 = r0.getNextFocusUpId()
            goto L_0x00b2
        L_0x0082:
            int r14 = r0.getNextFocusRightId()
            goto L_0x00b2
        L_0x0087:
            int r14 = r0.getNextFocusLeftId()
            goto L_0x00b2
        L_0x008c:
            if (r14 == r8) goto L_0x00ae
            if (r14 == r7) goto L_0x00a9
            if (r14 == r6) goto L_0x009f
            if (r14 == r5) goto L_0x0095
            goto L_0x0076
        L_0x0095:
            int r14 = r0.getNextFocusDownId()
            int r3 = r13.mCurPosition
            int r3 = r3 + r8
            r13.mCurPosition = r3
            goto L_0x00b2
        L_0x009f:
            int r14 = r0.getNextFocusUpId()
            int r3 = r13.mCurPosition
            int r3 = r3 - r8
            r13.mCurPosition = r3
            goto L_0x00b2
        L_0x00a9:
            int r14 = r0.getNextFocusRightId()
            goto L_0x00b2
        L_0x00ae:
            int r14 = r0.getNextFocusLeftId()
        L_0x00b2:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "第一次计算得到的下一个View的id:   0x"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = java.lang.Integer.toHexString(r14)
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.szchoiceway.zxwlib.utils.LogUtils.i(r1, r3)
            int r3 = com.szchoiceway.zxwlib.R.integer.jump_to_nextpage
            if (r14 != r3) goto L_0x01b8
            java.lang.String r3 = r0.getPage()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "第二次计算得到的当前View的页面位置:   "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r3)
            java.lang.String r4 = r4.toString()
            com.szchoiceway.zxwlib.utils.LogUtils.i(r1, r4)
            int[] r4 = new int[r7]
            int[] r5 = new int[r7]
            r6 = 0
            java.lang.String r9 = "page2"
            boolean r3 = r9.equals(r3)
            if (r3 == 0) goto L_0x0172
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r9 = "getNextFocusViewId: "
            java.lang.StringBuilder r3 = r3.append(r9)
            int r9 = r0.getViewId()
            java.lang.String r9 = java.lang.Integer.toHexString(r9)
            java.lang.StringBuilder r3 = r3.append(r9)
            java.lang.String r3 = r3.toString()
            android.util.Log.i(r1, r3)
            android.view.View r3 = r13.mCurParentView2
            int r0 = r0.getViewId()
            android.view.View r0 = r3.findViewById(r0)
            r0.getLocationOnScreen(r4)
            java.util.Map<java.lang.Integer, com.szchoiceway.zxwlib.focus.ViewInfo> r0 = r13.mFocusViewMap
            java.util.Set r0 = r0.keySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x012d:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x01b8
            java.lang.Object r3 = r0.next()
            java.lang.Integer r3 = (java.lang.Integer) r3
            java.util.Map<java.lang.Integer, com.szchoiceway.zxwlib.focus.ViewInfo> r9 = r13.mFocusViewMap
            java.lang.Object r3 = r9.get(r3)
            com.szchoiceway.zxwlib.focus.ViewInfo r3 = (com.szchoiceway.zxwlib.focus.ViewInfo) r3
            int r3 = r3.getViewId()
            android.view.View r9 = r13.mCurParentView
            android.view.View r9 = r9.findViewById(r3)
            r9.getLocationOnScreen(r5)
            double r9 = r13.getPointDistance(r4, r5)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "第二次计算得到的当前View的距离:   "
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.StringBuilder r11 = r11.append(r9)
            java.lang.String r11 = r11.toString()
            com.szchoiceway.zxwlib.utils.LogUtils.i(r1, r11)
            int r11 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r11 <= 0) goto L_0x016e
            if (r8 == 0) goto L_0x012d
        L_0x016e:
            r8 = r2
            r14 = r3
            r6 = r9
            goto L_0x012d
        L_0x0172:
            android.view.View r3 = r13.mCurParentView
            int r0 = r0.getViewId()
            android.view.View r0 = r3.findViewById(r0)
            r0.getLocationOnScreen(r4)
            java.util.Map<java.lang.Integer, com.szchoiceway.zxwlib.focus.ViewInfo> r0 = r13.mFocusViewMap2
            java.util.Set r0 = r0.keySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x0189:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x01b8
            java.lang.Object r3 = r0.next()
            java.lang.Integer r3 = (java.lang.Integer) r3
            java.util.Map<java.lang.Integer, com.szchoiceway.zxwlib.focus.ViewInfo> r9 = r13.mFocusViewMap2
            java.lang.Object r3 = r9.get(r3)
            com.szchoiceway.zxwlib.focus.ViewInfo r3 = (com.szchoiceway.zxwlib.focus.ViewInfo) r3
            int r3 = r3.getViewId()
            android.view.View r9 = r13.mCurParentView2
            android.view.View r9 = r9.findViewById(r3)
            r9.getLocationOnScreen(r5)
            double r9 = r13.getPointDistance(r4, r5)
            int r11 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r11 <= 0) goto L_0x01b4
            if (r8 == 0) goto L_0x0189
        L_0x01b4:
            r8 = r2
            r14 = r3
            r6 = r9
            goto L_0x0189
        L_0x01b8:
            r2 = r14
        L_0x01b9:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r0 = "完整计算得到的下一个View的:   0x"
            java.lang.StringBuilder r14 = r14.append(r0)
            java.lang.String r0 = java.lang.Integer.toHexString(r2)
            java.lang.StringBuilder r14 = r14.append(r0)
            java.lang.String r14 = r14.toString()
            com.szchoiceway.zxwlib.utils.LogUtils.i(r1, r14)
            java.util.Map<java.lang.Integer, com.szchoiceway.zxwlib.focus.ViewInfo> r14 = r13.mAllFocusViewMap
            java.lang.Integer r0 = java.lang.Integer.valueOf(r2)
            java.lang.Object r14 = r14.get(r0)
            com.szchoiceway.zxwlib.focus.ViewInfo r14 = (com.szchoiceway.zxwlib.focus.ViewInfo) r14
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.zxwlib.focus.FocusObserver.getNextFocusViewId(int):com.szchoiceway.zxwlib.focus.ViewInfo");
    }

    private double getPointDistance(int[] iArr, int[] iArr2) {
        return Math.sqrt(Math.pow((double) (iArr2[1] - iArr[1]), 2.0d) + Math.pow((double) (iArr2[0] - iArr[0]), 2.0d));
    }

    private boolean isNeedListChild(View view) {
        if (!(view instanceof ViewGroup) || (view instanceof FocusListView) || (view instanceof RecyclerView) || (view instanceof FocusIconWithTitleView)) {
            return false;
        }
        return true;
    }

    private boolean isNeedAdd(View view) {
        ViewParent parent = view.getParent();
        if (parent != null) {
            if (((ViewGroup) parent).getVisibility() == 0 && view.getVisibility() == 0) {
                return true;
            }
        } else if (view.getVisibility() == 0) {
            return true;
        }
        return false;
    }

    public int getmCurFocusViewId() {
        return this.mCurFocusViewId;
    }

    public void setmCurFocusViewId(int i) {
        View viewById;
        View viewById2;
        clearOldFocus((View) null);
        ViewInfo viewInfo = this.mAllFocusViewMap.get(Integer.valueOf(i));
        if (viewInfo != null) {
            this.mCurFocusPage = viewInfo.getPage();
        }
        Log.i(TAG, "setmCurFocusViewId: " + Integer.toHexString(i) + " | " + Integer.toHexString(this.mCurFocusViewId));
        int i2 = this.mCurFocusViewId;
        if (!(i == i2 || (viewById2 = getViewById(i2)) == null)) {
            clearAppointFocus(viewById2);
        }
        this.mOldFocusViewId = this.mCurFocusViewId;
        this.mCurFocusViewId = i;
        if (this.mControlByKnob && (viewById = getViewById(i)) != null) {
            this.oldFocusedView = viewById;
            drawFocusDirection(viewById);
        }
        sendFocusChangeInfo();
    }

    private void sendFocusChangeInfo() {
        int i;
        int i2;
        OnFocusViewChangeListener onFocusViewChangeListener = this.mOnFocusViewChangeListener;
        if (onFocusViewChangeListener != null && (i = this.mCurFocusViewId) != (i2 = this.mOldFocusViewId)) {
            onFocusViewChangeListener.onChange(i2, i);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0027, code lost:
        r4 = r3.mCurParentView2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.view.View getViewById(int r4) {
        /*
            r3 = this;
            java.util.Map<java.lang.Integer, com.szchoiceway.zxwlib.focus.ViewInfo> r0 = r3.mAllFocusViewMap
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            java.lang.Object r4 = r0.get(r4)
            com.szchoiceway.zxwlib.focus.ViewInfo r4 = (com.szchoiceway.zxwlib.focus.ViewInfo) r4
            r0 = 0
            if (r4 == 0) goto L_0x002f
            int r1 = r4.getViewId()
            java.lang.String r4 = r4.getPage()
            android.view.View r2 = r3.mCurParentView
            if (r2 == 0) goto L_0x001f
            android.view.View r0 = r2.findViewById(r1)
        L_0x001f:
            java.lang.String r2 = "page2"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x002f
            android.view.View r4 = r3.mCurParentView2
            if (r4 == 0) goto L_0x002f
            android.view.View r0 = r4.findViewById(r1)
        L_0x002f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szchoiceway.zxwlib.focus.FocusObserver.getViewById(int):android.view.View");
    }

    public int getmCurPosition() {
        return this.mCurPosition;
    }

    public void setmCurPosition(int i) {
        FocusAdapter focusAdapter;
        this.mCurPosition = i;
        View viewById = getViewById(this.mCurFocusViewId);
        if (viewById == null) {
            return;
        }
        if (viewById instanceof FocusRecycleView) {
            FocusRecyclerViewAdapter focusRecyclerViewAdapter = (FocusRecyclerViewAdapter) ((FocusRecycleView) viewById).getAdapter();
            if (focusRecyclerViewAdapter != null) {
                focusRecyclerViewAdapter.setCurFocusItemPosition(i);
            }
        } else if ((viewById instanceof FocusListView) && (focusAdapter = (FocusAdapter) ((FocusListView) viewById).getAdapter()) != null) {
            focusAdapter.setCurFocusItemPosition(i);
        }
    }

    public String getmCurFocusPage() {
        return this.mCurFocusPage;
    }

    public void setmControlByKnob(boolean z) {
        this.mControlByKnob = z;
    }

    public boolean getControlByKnob() {
        return this.mControlByKnob;
    }

    public void setOnFocusViewChangeListener(OnFocusViewChangeListener onFocusViewChangeListener) {
        this.mOnFocusViewChangeListener = onFocusViewChangeListener;
    }
}
