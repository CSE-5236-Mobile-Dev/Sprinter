package com.pearlsea.sprinter.databinding;
import com.pearlsea.sprinter.R;
import com.pearlsea.sprinter.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentSignupBindingImpl extends FragmentSignupBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.txtHelloWelcome, 1);
        sViewsWithIds.put(R.id.nameLabel, 2);
        sViewsWithIds.put(R.id.nameTextBox, 3);
        sViewsWithIds.put(R.id.emailLabel, 4);
        sViewsWithIds.put(R.id.emailAddressTextBox, 5);
        sViewsWithIds.put(R.id.passwordLabel, 6);
        sViewsWithIds.put(R.id.passwordTextBox, 7);
        sViewsWithIds.put(R.id.frameStackellipseone, 8);
        sViewsWithIds.put(R.id.viewEllipseOne, 9);
        sViewsWithIds.put(R.id.txtSIGNUP, 10);
        sViewsWithIds.put(R.id.signupBackToWelcome, 11);
        sViewsWithIds.put(R.id.viewEllipseTwo, 12);
        sViewsWithIds.put(R.id.txtBACK, 13);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentSignupBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private FragmentSignupBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.EditText) bindings[5]
            , (android.widget.TextView) bindings[4]
            , (android.widget.FrameLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.TextView) bindings[2]
            , (android.widget.EditText) bindings[3]
            , (android.widget.TextView) bindings[6]
            , (android.widget.EditText) bindings[7]
            , (android.widget.FrameLayout) bindings[11]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[10]
            , (android.view.View) bindings[9]
            , (android.view.View) bindings[12]
            );
        this.linearLoginPage.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}